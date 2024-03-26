package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.exception.UnsupportedCityException;
import com.fujitsu.deliveryfee.exception.UnsupportedVehicleTypeException;
import com.fujitsu.deliveryfee.exception.VehicleUseForbiddenException;
import com.fujitsu.deliveryfee.exception.WeatherDataUnavailableException;
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
        city = city.toLowerCase();
        vehicleType = vehicleType.toLowerCase();

        String stationName = mapCityToStationName(city);
        double baseFee = calculateBaseFee(city, vehicleType);
        double weatherFees = calculateWeatherFees(stationName, vehicleType);
        return baseFee + weatherFees;
    }

    private double calculateBaseFee(String city, String vehicleType) {
        switch (city) {
            case "tallinn":
                switch (vehicleType) {
                    case "car": return 4.0;
                    case "scooter": return 3.5;
                    case "bike": return 3.0;
                    default: throw new UnsupportedVehicleTypeException(vehicleType);
                }
            case "tartu":
                switch (vehicleType) {
                    case "car": return 3.5;
                    case "scooter": return 3.0;
                    case "bike": return 2.5;
                    default: throw new UnsupportedVehicleTypeException(vehicleType);
                }
            case "pärnu":
                switch (vehicleType) {
                    case "car": return 3.0;
                    case "scooter": return 2.5;
                    case "bike": return 2.0;
                    default: throw new UnsupportedVehicleTypeException(vehicleType);
                }
            default: throw new UnsupportedCityException(city);
        }
    }

    private double calculateWeatherFees(String stationName, String vehicleType) {
        WeatherData latestWeather = weatherDataRepository.findLatestByCity(stationName);
        if (latestWeather == null) {
            throw new WeatherDataUnavailableException(stationName);
        }
        double fee = 0.0;



        // Check weather conditions that affect both scooters and bikes
        if ("scooter".equals(vehicleType) || "bike".equals(vehicleType)) {
            if (latestWeather.getAirTemperature() < -10) {
                fee += 1.0;
            } else if (latestWeather.getAirTemperature() >= -10 && latestWeather.getAirTemperature() <= 0) {
                fee += 0.5;
            }
        }


            String phenomenon = latestWeather.getWeatherPhenomenon();
            if (phenomenon != null) {
                if (phenomenon.toLowerCase().contains("snow") || phenomenon.toLowerCase().contains("sleet")) {
                    fee += 1.0;
                } else if (phenomenon.toLowerCase().contains("rain")) {
                    fee += 0.5;
                } else if (phenomenon.toLowerCase().contains("glaze") || phenomenon.toLowerCase().contains("hail") || phenomenon.toLowerCase().contains("thunder")) {
                    throw new VehicleUseForbiddenException();
                }
            }


        // Additional weather conditions specific to bikes
        if ("bike".equals(vehicleType)) {
            if (latestWeather.getWindSpeed() != null && latestWeather.getWindSpeed() >= 10 && latestWeather.getWindSpeed() <= 20) {
                fee += 0.5;
            } else if (latestWeather.getWindSpeed() != null && latestWeather.getWindSpeed() > 20) {
                throw new VehicleUseForbiddenException();
            }
        }

        return fee;
    }

    private String mapCityToStationName(String city) {
        switch (city) {
            case "tallinn":
                return "Tallinn-Harku";
            case "tartu":
                return "Tartu-Tõravere";
            case "pärnu":
                return "Pärnu";
            default:
                throw new UnsupportedCityException(city);
        }
    }
}
