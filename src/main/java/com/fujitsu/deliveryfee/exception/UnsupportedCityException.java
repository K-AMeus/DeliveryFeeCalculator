package com.fujitsu.deliveryfee.exception;

public class UnsupportedCityException extends RuntimeException{
    public UnsupportedCityException(String city) {
        super("Unsupported city: " + city);
    }
}
