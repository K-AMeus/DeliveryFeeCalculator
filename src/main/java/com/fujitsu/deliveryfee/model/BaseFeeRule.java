package com.fujitsu.deliveryfee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BaseFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;
    private String vehicleType;
    private Double fee;

    public BaseFeeRule() {
    }

    public BaseFeeRule(Long id, String city, String vehicleType, Double fee) {
        this.id = id;
        this.city = city;
        this.vehicleType = vehicleType;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Double getFee() {
        return fee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
