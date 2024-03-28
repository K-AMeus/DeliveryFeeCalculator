package com.fujitsu.deliveryfee.exception;


/**
 * Custom exception thrown when an unsupported vehicle type is specified in a
 * delivery fee calculation request.
 */
public class UnsupportedVehicleTypeException extends RuntimeException{
    public UnsupportedVehicleTypeException(String vehicleType) {
        super("Unsupported vehicle type: " + vehicleType);
    }
}