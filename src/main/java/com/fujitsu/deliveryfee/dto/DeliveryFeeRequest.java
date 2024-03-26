package com.fujitsu.deliveryfee.dto;

public class DeliveryFeeRequest {
    private String city;
    private String vehicleType;


    public DeliveryFeeRequest() {
    }

    public String getCity() {
        return city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
