package com.fujitsu.deliveryfee.exception;

public class WeatherDataUnavailableException extends RuntimeException{
    public WeatherDataUnavailableException(String stationName) {
        super("No weather data available for station: " + stationName);
    }
}
