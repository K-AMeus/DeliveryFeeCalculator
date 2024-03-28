package com.fujitsu.deliveryfee.exception;


/**
 * Custom exception thrown when there is an error processing weather data,
 * such as when parsing or fetching weather information fails.
 */
public class WeatherDataProcessingException extends RuntimeException{
    public WeatherDataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
