package com.fujitsu.deliveryfee.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


/**
 * Represents weather data for a specific station at a specific point in time.
 * This entity stores temperature, wind speed, and weather phenomena observed,
 * which are crucial in calculating delivery fees under various conditions.
 */
@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String stationName;

    // Nullable - some stations don't report wmoCode, airTemperature, windSpeed and weatherPhenomenon
    private String wmoCode; // World Meteorological Organization code for the station
    private Double airTemperature; // Air temperature in degrees Celsius
    private Double windSpeed; // Wind speed in meters per second
    private String weatherPhenomenon; // Descriptive weather condition (e.g., clear, rain, snow)
    @NotNull
    private LocalDateTime timestamp; // The timestamp of the weather observation

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

    public WeatherData(String stationName, Double airTemperature, Double windSpeed, String weatherPhenomenon, LocalDateTime timestamp) {
        this.stationName = stationName;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timestamp = timestamp;
    }

    public WeatherData(Double airTemperature, Double windSpeed, String weatherPhenomenon, LocalDateTime timestamp) {
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
