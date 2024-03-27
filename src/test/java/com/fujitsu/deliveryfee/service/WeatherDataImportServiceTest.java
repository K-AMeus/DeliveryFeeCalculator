package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.exception.WeatherDataProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit tests for the {@link WeatherDataImportService} class.
 *
 * These tests ensure that the service responsible for importing weather data behaves correctly under various scenarios,
 * including error handling when dealing with malformed XML data.
 */
@ExtendWith(MockitoExtension.class)
public class WeatherDataImportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherDataImportService weatherDataImportService;


    /**
     * Tests the behavior of the {@link WeatherDataImportService#fetchAndSaveWeatherData} method
     * when it encounters malformed XML data.
     *
     * This test simulates a scenario where the external weather data source returns malformed XML.
     * The test verifies that the {@link WeatherDataImportService} correctly identifies the issue and
     * throws a {@link WeatherDataProcessingException}, ensuring the application's robustness in the face of
     * invalid external data.
     */
    @Test
    void whenXmlDataIsMalformed_thenThrowWeatherDataProcessingException() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("malformed xml");
        assertThrows(WeatherDataProcessingException.class, () -> weatherDataImportService.fetchAndSaveWeatherData(),
                "Malformed XML data should throw a WeatherDataProcessingException.");
    }

}
