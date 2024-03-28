package com.fujitsu.deliveryfee.exception;


/**
 * Custom exception thrown when the use of a selected vehicle type is forbidden
 * under adverse weather conditions.
 */
public class VehicleUseForbiddenException extends RuntimeException{
    public VehicleUseForbiddenException() {
        super("Usage of selected vehicle type is forbidden");
    }
}
