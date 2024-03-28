package com.fujitsu.deliveryfee.dto;

import java.time.LocalDateTime;

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