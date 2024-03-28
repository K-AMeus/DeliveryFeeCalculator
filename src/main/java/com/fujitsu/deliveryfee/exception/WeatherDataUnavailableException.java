package com.fujitsu.deliveryfee.exception;

/**
 * Custom exception thrown when weather data is not available for a specified
 * station, preventing the calculation of delivery fees that depend on weather conditions.
 */
public class WeatherDataUnavailableException extends RuntimeException{
    public WeatherDataUnavailableException(String stationName) {
        super("No weather data available for station: " + stationName);
    }
}
