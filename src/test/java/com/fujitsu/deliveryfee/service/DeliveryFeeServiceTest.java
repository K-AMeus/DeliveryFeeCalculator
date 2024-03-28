package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.exception.UnsupportedCityException;
import com.fujitsu.deliveryfee.exception.UnsupportedVehicleTypeException;
import com.fujitsu.deliveryfee.exception.VehicleUseForbiddenException;
import com.fujitsu.deliveryfee.exception.WeatherDataUnavailableException;
import com.fujitsu.deliveryfee.model.WeatherData;
import com.fujitsu.deliveryfee.repository.WeatherDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;


/**
 * Tests for {@link DeliveryFeeService}.
 *
 * This class covers unit tests for delivery fee calculations considering different cities,
 * vehicle types, and weather conditions. It aims to validate both normal operations and
 * error handling cases, ensuring the service behaves correctly under various scenarios.
 *
 * Tests are organized into:
 * 1) Base fee calculations - Validates fees based solely on city and vehicle type.
 * 2) Weather condition impacts - Examines how different weather conditions affect the fee.
 * 3) Exception scenarios - Checks proper handling of unsupported cases and data unavailability.
 */
@ExtendWith(MockitoExtension.class)
class DeliveryFeeServiceTest {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private DeliveryFeeService deliveryFeeService;

    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2024, 3, 28, 12, 0); // Example date and time

    private WeatherData defaultWeatherData() {
        return new WeatherData(10.0, 5.0, "clear", TEST_DATE_TIME);
    }



    /**
     * Validates base fee calculations across different cities and vehicle types.
     * Utilizes parameterized tests to cover a variety of scenarios to ensure the correct base fee is applied.
     *
     * @param city The city for which the delivery fee is being calculated.
     * @param vehicleType The type of vehicle used for delivery.
     * @param expectedFee The expected delivery fee based on city and vehicle type.
     */
    @ParameterizedTest
    @CsvSource({
            "Tallinn, car, 4.0",
            "Tallinn, scooter, 3.5",
            "Tallinn, bike, 3.0",
            "Tartu, car, 3.5",
            "Tartu, scooter, 3.0",
            "Tartu, bike, 2.5",
            "Pärnu, car, 3.0",
            "Pärnu, scooter, 2.5",
            "Pärnu, bike, 2.0"
    })
    void testBaseFeeCalculation(String city, String vehicleType, double expectedFee) {
        when(weatherDataRepository.findByCityAndTimestampBefore(anyString(), any(LocalDateTime.class), any(Pageable.class))).thenReturn(Collections.singletonList(defaultWeatherData()));
        double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, TEST_DATE_TIME);
        assertEquals(expectedFee, fee, "Incorrect base fee for " + vehicleType + " in " + city);
    }




    /**
     * Tests the impact of different weather conditions on the delivery fee.
     * This parameterized test method evaluates how air temperature, wind speed,
     * and weather phenomena affect the total delivery fee for different vehicle types.
     * At the moment it only tests Tallinn, but other cities can be implemented easily.
     * It ensures that the service applies extra fees or restrictions according
     * to predefined business rules under varying weather conditions.
     *
     * @param city The city where the delivery occurs.
     * @param vehicleType The type of vehicle used for the delivery.
     * @param temperature The current air temperature.
     * @param windSpeed The current wind speed.
     * @param phenomenon The current weather phenomenon.
     * @param expectedFee The expected total delivery fee after considering weather conditions.
     */
    @ParameterizedTest
    @MethodSource("weatherConditionProvider")
    void testWeatherConditionImpact(String city, String vehicleType, Double temperature, Double windSpeed, String phenomenon, double expectedFee) {
        WeatherData weatherData = new WeatherData(temperature, windSpeed, phenomenon, TEST_DATE_TIME);
        when(weatherDataRepository.findByCityAndTimestampBefore(anyString(), any(LocalDateTime.class), any(Pageable.class))).thenReturn(Collections.singletonList(weatherData));
        double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, TEST_DATE_TIME);
        assertEquals(expectedFee, fee, "Incorrect fee for " + vehicleType + " in " + city + " under specified weather conditions.");
    }

    private static Stream<Arguments> weatherConditionProvider() {
        return Stream.of(
                // Air temperature tests
                arguments("Tallinn", "scooter", -15.0, 5.0, "clear", 4.5),
                arguments("Tallinn", "scooter", -5.0, 5.0, "clear", 4.0),
                arguments("Tallinn", "bike", -15.0, 5.0, "clear", 4.0),
                arguments("Tallinn", "bike", -5.0, 5.0, "clear", 3.5),
                arguments("Tallinn", "bike",  5.0, 5.0, "clear", 3.0),
                arguments("Tallinn", "scooter", 5.0, 5.0, "clear", 3.5),
                arguments("Tallinn", "scooter", null, 5.0, "clear", 3.5),
                arguments("Tallinn", "bike", null, 5.0, "clear", 3.0),


                // Wind speed tests
                arguments("Tallinn", "bike", 5.0, 15.0, "clear", 3.5),
                //arguments("Tallinn", "bike", 5.0, 21.0, "clear", 3.0), // Throws an exception, additional test cases below
                arguments("Tallinn", "bike", 5.0, 5.0, "clear", 3.0),
                arguments("Tallinn", "bike", 5.0, null, "clear", 3.0),

                // Weather phenomenon tests
                arguments("Tallinn", "scooter", 5.0, 5.0, "snow", 4.5),
                arguments("Tallinn", "scooter", 5.0, 5.0, "sleet", 4.5),
                arguments("Tallinn", "bike", 5.0, 5.0, "snow", 4.0),
                arguments("Tallinn", "bike", 5.0, 5.0, "sleet", 4.0),
                arguments("Tallinn", "bike", 5.0, 5.0, "rain", 3.5),
                arguments("Tallinn", "scooter", 5.0, 5.0, "rain", 4.0),
                arguments("Tallinn", "scooter", 5.0, 5.0, "clear", 3.5),
                arguments("Tallinn", "bike", 5.0, 5.0, "clear", 3),
                arguments("Tallinn", "scooter", 5.0, 5.0, "null", 3.5),
                arguments("Tallinn", "bike", 5.0, 5.0, "null", 3.0)
                // Test cases for glaze, hail and thunder are below

        );
    }





    /**
     * Test scenarios where vehicle use is forbidden due to adverse weather conditions.
     * This method leverages parameterized tests from a custom provider to verify that
     * the application correctly throws {@link VehicleUseForbiddenException} under
     * hazardous weather conditions for any given city and vehicle type combination.
     *
     * @param city The city where the delivery is attempted.
     * @param vehicleType The type of vehicle used for the attempted delivery.
     * @param temperature The air temperature at the time of delivery.
     * @param windSpeed The wind speed at the time of delivery.
     * @param phenomenon The weather phenomenon at the time of delivery.
     */
    @ParameterizedTest
    @MethodSource("forbiddenWeatherConditions")
    void testForbiddenWeatherConditions(String city, String vehicleType, Double temperature, Double windSpeed, String phenomenon) {
        WeatherData hazardousWeather = new WeatherData(temperature, windSpeed, phenomenon, TEST_DATE_TIME);
        when(weatherDataRepository.findByCityAndTimestampBefore(eq("Tallinn-Harku"), eq(TEST_DATE_TIME), any(Pageable.class)))
                .thenReturn(Collections.singletonList(hazardousWeather));
        assertThrows(VehicleUseForbiddenException.class,
                () -> deliveryFeeService.calculateDeliveryFee(city, vehicleType, TEST_DATE_TIME),
                String.format("Usage of %s should be forbidden under weather conditions: %s", vehicleType, phenomenon));
    }


    private static Stream<Arguments> forbiddenWeatherConditions() {
        return Stream.of(
                Arguments.of("Tallinn", "scooter", 5.0, 21.0, "glaze"),
                Arguments.of("Tallinn", "scooter", 5.0, 21.0, "hail"),
                Arguments.of("Tallinn", "scooter", 5.0, 21.0, "thunder"),
                Arguments.of("Tallinn", "bike", 5.0, 21.0, "glaze"),
                Arguments.of("Tallinn", "bike", 5.0, 21.0, "hail"),
                Arguments.of("Tallinn", "bike", 5.0, 21.0, "thunder")
        );
    }





    /**
     * Verifies that an {@link UnsupportedVehicleTypeException} is thrown when an unsupported vehicle type is used.
     */
    @Test
    void whenUnsupportedVehicleType_thenThrowException() {
        assertThrows(UnsupportedVehicleTypeException.class, () -> deliveryFeeService.calculateDeliveryFee("Tallinn", "tank", TEST_DATE_TIME));
    }


    /**
     * Verifies that an {@link UnsupportedCityException} is thrown for delivery requests to unsupported cities.
     */
    @Test
    void whenUnsupportedCity_thenThrowException() {
        assertThrows(UnsupportedCityException.class, () -> deliveryFeeService.calculateDeliveryFee("UnknownCity", "bike", TEST_DATE_TIME));
    }


    /**
     * Verifies that a {@link WeatherDataUnavailableException} is thrown when weather data is not available.
     */
    @Test
    void whenWeatherDataUnavailable_thenThrowException() {
        when(weatherDataRepository.findByCityAndTimestampBefore(anyString(), any(LocalDateTime.class), any(Pageable.class))).thenReturn(Collections.emptyList());
        assertThrows(WeatherDataUnavailableException.class, () -> deliveryFeeService.calculateDeliveryFee("Tallinn", "bike", TEST_DATE_TIME));
    }


    /**
     * Ensures that using a bike under extremely high wind conditions correctly throws a {@link VehicleUseForbiddenException}.
     */

    @Test
    void whenInTallinnWithBikeAndWindSpeedIsGreaterThan20_thenVehicleUseIsForbidden() {
        WeatherData extremelyWindyWeather = new WeatherData("Tallinn-Harku", 5.0, 21.0, "clear", TEST_DATE_TIME);
        when(weatherDataRepository.findByCityAndTimestampBefore("Tallinn-Harku", TEST_DATE_TIME, PageRequest.of(0, 1))).thenReturn(Collections.singletonList(extremelyWindyWeather));
        assertThrows(VehicleUseForbiddenException.class, () -> deliveryFeeService.calculateDeliveryFee("Tallinn", "bike", TEST_DATE_TIME));
    }

}
