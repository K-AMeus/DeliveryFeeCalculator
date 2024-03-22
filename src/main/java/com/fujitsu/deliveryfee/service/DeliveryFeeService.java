package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.model.WeatherData;
import com.fujitsu.deliveryfee.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService {

    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public DeliveryFeeService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public double calculateDeliveryFee(String city, String vehicleType) {
        String stationName = mapCityToStationName(city);
        double baseFee = calculateBaseFee(city, vehicleType);
        double weatherFees = calculateWeatherFees(stationName, vehicleType);
        return baseFee + weatherFees;
    }

    private double calculateBaseFee(String city, String vehicleType) {
        switch (city) {
            case "Tallinn":
                switch (vehicleType) {
                    case "Car": return 4.0;
                    case "Scooter": return 3.5;
                    case "Bike": return 3.0;
                    default: throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
                }
            case "Tartu":
                switch (vehicleType) {
                    case "Car": return 3.5;
                    case "Scooter": return 3.0;
                    case "Bike": return 2.5;
                    default: throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
                }
            case "Pärnu":
                switch (vehicleType) {
                    case "Car": return 3.0;
                    case "Scooter": return 2.5;
                    case "Bike": return 2.0;
                    default: throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
                }
            default: throw new IllegalArgumentException("Unsupported city: " + city);
        }
    }

    private double calculateWeatherFees(String stationName, String vehicleType) {
        WeatherData latestWeather = weatherDataRepository.findLatestByCity(stationName);
        if (latestWeather == null) {
            throw new IllegalStateException("No weather data available for station: " + stationName);
        }
        double fee = 0.0;

        if ("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) {
            if (latestWeather.getAirTemperature() < -10) {
                fee += 1.0;
            } else if (latestWeather.getAirTemperature() >= -10 && latestWeather.getAirTemperature() <= 0) {
                fee += 0.5;
            }

            if ("Snow".equals(latestWeather.getWeatherPhenomenon()) || "Sleet".equals(latestWeather.getWeatherPhenomenon())) {
                fee += 1.0;
            } else if ("Rain".equals(latestWeather.getWeatherPhenomenon())) {
                fee += 0.5;
            }
        }

        if ("Bike".equals(vehicleType)) {
            if (latestWeather.getWindSpeed() != null && latestWeather.getWindSpeed() >= 10 && latestWeather.getWindSpeed() < 20) {
                fee += 0.5;
            } else if (latestWeather.getWindSpeed() != null && latestWeather.getWindSpeed() >= 20) {
                throw new IllegalStateException("Usage of selected vehicle type is forbidden due to high wind speed");
            }
        }

        return fee;
    }

    private String mapCityToStationName(String city) {
        switch (city) {
            case "Tallinn":
                return "Tallinn-Harku";
            case "Tartu":
                return "Tartu-Tõravere";
            case "Pärnu":
                return "Pärnu";
            default:
                throw new IllegalArgumentException("Unsupported city: " + city);
        }
    }
}
