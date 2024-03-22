package com.fujitsu.deliveryfee.integration;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "station")
public class StationData {
    private String name;
    private String wmoCode;
    private Double airTemperature;
    private Double windSpeed;
    private String weatherPhenomenon;

    public StationData() {
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "windspeed")
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @XmlElement(name = "phenomenon")
    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
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

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "wmocode")
    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    @XmlElement(name = "airtemperature")
    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }


}
