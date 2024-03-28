package com.fujitsu.deliveryfee.dto;

import java.time.LocalDateTime;


/**
 * A data transfer object (DTO) representing a request to calculate delivery fees.
 * This DTO encapsulates all necessary parameters required for requesting a delivery fee
 * calculation, including the city where the delivery is taking place, the type of vehicle used,
 * and optionally, the specific date and time for which the calculation is requested.
 *
 * This class enables the encapsulation of request parameters into a single object,
 * simplifying the method signatures in the controller and service layers and allowing for
 * easier data manipulation and validation.
 */
public class DeliveryFeeRequest {
    private String city;
    private String vehicleType;
    private LocalDateTime dateTime;


    public DeliveryFeeRequest() {
    }

    public String getCity() {
        return city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}