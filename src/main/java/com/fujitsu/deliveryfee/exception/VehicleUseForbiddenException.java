package com.fujitsu.deliveryfee.exception;

public class VehicleUseForbiddenException extends RuntimeException{
    public VehicleUseForbiddenException() {
        super("Usage of selected vehicle type is forbidden");
    }
}
