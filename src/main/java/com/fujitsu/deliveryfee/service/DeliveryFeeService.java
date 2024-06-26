package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.exception.UnsupportedCityException;
import com.fujitsu.deliveryfee.exception.UnsupportedVehicleTypeException;
import com.fujitsu.deliveryfee.exception.VehicleUseForbiddenException;
import com.fujitsu.deliveryfee.exception.WeatherDataUnavailableException;
import com.fujitsu.deliveryfee.model.WeatherData;
import com.fujitsu.deliveryfee.repository.WeatherDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Service for calculating delivery fees based on various factors including
 * city, vehicle type, and current weather conditions. Utilizes weather data
 * to apply additional fees as per the business rules.
 */
@Service
public class DeliveryFeeService {

    private final WeatherDataRepository weatherDataRepository;
    private static final Logger log = LoggerFactory.getLogger(DeliveryFeeService.class);

    @Autowired
    public DeliveryFeeService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }



    /**
     * Calculates the delivery fee for a given city and vehicle type, taking into
     * account the current weather conditions. Applies base fees and additional
     * weather-related fees as necessary.
     *
     * @param city the city where the delivery is taking place
     * @param vehicleType the type of vehicle used for the delivery
     * @param dateTime the datetime for which the fee is being calculated
     * @return the total calculated delivery fee
     * @throws UnsupportedCityException if the city is not supported
     * @throws UnsupportedVehicleTypeException if the vehicle type is not supported
     * @throws VehicleUseForbiddenException if the vehicle use is forbidden under current weather conditions
     * @throws WeatherDataUnavailableException if weather data is unavailable for the given time
     */
    public double calculateDeliveryFee(String city, String vehicleType, LocalDateTime dateTime) {
        log.info("Calculating delivery fee for city: {}, vehicle type: {}, dateTime: {}", city, vehicleType, dateTime);
        city = city.toLowerCase();
        vehicleType = vehicleType.toLowerCase();

        String stationName = mapCityToStationName(city);
        double baseFee = calculateBaseFee(city, vehicleType);
        double weatherFees = calculateWeatherFees(stationName, vehicleType, dateTime);
        double totalFee = baseFee + weatherFees;
        log.info("Total delivery fee calculated: {} for city: {}, vehicle type: {}, dateTime: {}", totalFee, city, vehicleType, dateTime);

        return totalFee;
    }



    private double calculateBaseFee(String city, String vehicleType) {
        switch (city) {
            case "tallinn":
                switch (vehicleType) {
                    case "car": return 4.0;
                    case "scooter": return 3.5;
                    case "bike": return 3.0;
                    default: throw new UnsupportedVehicleTypeException(vehicleType);
                }
            case "tartu":
                switch (vehicleType) {
                    case "car": return 3.5;
                    case "scooter": return 3.0;
                    case "bike": return 2.5;
                    default: throw new UnsupportedVehicleTypeException(vehicleType);
                }
            case "pärnu":
                switch (vehicleType) {
                    case "car": return 3.0;
                    case "scooter": return 2.5;
                    case "bike": return 2.0;
                    default: throw new UnsupportedVehicleTypeException(vehicleType);
                }
            default: throw new UnsupportedCityException(city);
        }
    }

    private double calculateWeatherFees(String stationName, String vehicleType, LocalDateTime dateTime) {
        List<WeatherData> weatherDataList = weatherDataRepository.findByCityAndTimestampBefore(
                stationName,
                dateTime != null ? dateTime : LocalDateTime.now(),
                PageRequest.of(0, 1) // Fetch only the top result
        );

        if (weatherDataList.isEmpty()) {
            throw new WeatherDataUnavailableException("No weather data available for " + stationName + " at the requested time.");
        }

        WeatherData latestWeather = weatherDataList.get(0);
        double fee = 0.0;


        // Check weather conditions that affect both scooters and bikes
        if ("scooter".equals(vehicleType) || "bike".equals(vehicleType)) {
            if (latestWeather.getAirTemperature() != null) {
                if (latestWeather.getAirTemperature() < -10) {
                    fee += 1.0;
                } else if (latestWeather.getAirTemperature() >= -10 && latestWeather.getAirTemperature() <= 0) {
                    fee += 0.5;
                }
            } else {
                log.warn("Station {} has not reported air temperature, calculated fee may not accurately reflect weather conditions.", stationName);
            }
        }


        if (latestWeather.getWeatherPhenomenon() != null) {
            String phenomenon = latestWeather.getWeatherPhenomenon().toLowerCase();
            if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
                fee += 1.0;
            } else if (phenomenon.contains("shower")) {
                if (phenomenon.contains("snow shower")) {
                    fee += 1.0;
                } else {
                    fee += 0.5;
                }
            } else if (phenomenon.contains("rain")) {
                fee += 0.5;
            } else if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder")) {
                throw new VehicleUseForbiddenException();
            }
        } else {
            log.warn("Station {} has not reported weather phenomenon, calculated fee may not accurately reflect weather conditions.", stationName);

        }


        // Additional weather conditions specific to bikes
        if ("bike".equals(vehicleType)) {
            if (latestWeather.getWindSpeed() != null) {
                if (latestWeather.getWindSpeed() >= 10 && latestWeather.getWindSpeed() <= 20) {
                    fee += 0.5;
                } else if (latestWeather.getWindSpeed() > 20) {
                    throw new VehicleUseForbiddenException();
                }
            } else {
                log.warn("Station {} has not reported wind speed, calculated fee may not accurately reflect weather conditions.", stationName);
            }
        }

        return fee;
    }

    private String mapCityToStationName(String city) {
        switch (city) {
            case "tallinn":
                return "Tallinn-Harku";
            case "tartu":
                return "Tartu-Tõravere";
            case "pärnu":
                return "Pärnu";
            default:
                throw new UnsupportedCityException(city);
        }
    }
}