package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.integration.Observations;
import com.fujitsu.deliveryfee.integration.StationData;
import com.fujitsu.deliveryfee.model.WeatherData;
import com.fujitsu.deliveryfee.repository.WeatherDataRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.LocalDateTime;



@Service
public class WeatherDataImportService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataImportService.class);

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public WeatherDataImportService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
    }

    @Scheduled(cron = "0 * * * * *") // This runs at 15 minutes past every hour CHANGE BACK TO @Scheduled(cron = "0 15 * * * *") WHEN FINISHED TESTING
    public void fetchAndSaveWeatherData() {
        logger.info("Starting to fetch weather data...");
        String xmlData = fetchWeatherDataXml();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Observations observations = (Observations) unmarshaller.unmarshal(new StringReader(xmlData));

            observations.getStations().stream()
                    .filter(station -> "Tallinn-Harku".equals(station.getName()) || "Tartu-Tõravere".equals(station.getName()) || "Pärnu".equals(station.getName()))
                    .forEach(this::processAndSaveStationData);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        logger.info("Weather data fetched and saved successfully.");
    }

    private String fetchWeatherDataXml() {
        String url = "https://ilmateenistus.ee/ilma_andmed/xml/observations.php";
        return restTemplate.getForObject(url, String.class);
    }

    private void processAndSaveStationData(StationData stationData) {
        WeatherData weatherData = new WeatherData();
        weatherData.setStationName(stationData.getName());
        weatherData.setWmoCode(stationData.getWmoCode());
        weatherData.setAirTemperature(stationData.getAirTemperature());

        // Check for null before setting the wind speed
        if (stationData.getWindSpeed() != null) {
            weatherData.setWindSpeed(stationData.getWindSpeed());
        } else {
            weatherData.setWindSpeed(null);
        }

        // Check for null before setting the weather phenomenon
        if (stationData.getWeatherPhenomenon() != null) {
            weatherData.setWeatherPhenomenon(stationData.getWeatherPhenomenon());
        } else {
            weatherData.setWeatherPhenomenon(null);
        }

        weatherData.setTimestamp(LocalDateTime.now());

        // Save to database
        weatherDataRepository.save(weatherData);
    }

}