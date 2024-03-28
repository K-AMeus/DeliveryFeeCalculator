package com.fujitsu.deliveryfee.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * Configuration properties class for externalizing weather station configuration.
 * This class binds properties prefixed with 'weather' from application.properties file
 * to a list of weather stations. It simplifies the management of weather stations' data,
 * allowing easy updates without code changes.
 *
 * Example usage in application.properties:
 * weather.stations=Tallinn-Harku,Tartu-Tõravere,Pärnu
 */
@Configuration
@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {

    private List<String> stations;

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }
}
