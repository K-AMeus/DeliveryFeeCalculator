package com.fujitsu.deliveryfee.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stationName;
    private String wmoCode;
    private Double airTemperature;
    private Double windSpeed;
    private String weatherPhenomenon;
    private LocalDateTime timestamp;

    public WeatherData() {
    }

    public WeatherData(Long id, String stationName, String wmoCode, Double airTemperature, Double windSpeed, String weatherPhenomenon, LocalDateTime timestamp) {
        this.id = id;
        this.stationName = stationName;
        this.wmoCode = wmoCode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getWmoCode() {
        return wmoCode;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
