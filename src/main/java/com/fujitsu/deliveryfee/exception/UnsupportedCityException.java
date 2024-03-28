package com.fujitsu.deliveryfee.exception;


/**
 * Custom exception thrown when a request is made for a city that is not supported
 * by the delivery fee calculation service.
 */
public class UnsupportedCityException extends RuntimeException{
    public UnsupportedCityException(String city) {
        super("Unsupported city: " + city);
    }
}
