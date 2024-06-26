package com.fujitsu.deliveryfee.service;

import com.fujitsu.deliveryfee.config.WeatherProperties;
import com.fujitsu.deliveryfee.exception.WeatherDataProcessingException;
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
import java.util.List;



/**
 * Service class for importing weather data from an external source.
 * Periodically fetches weather data XML and saves it to the database.
 */
@Service
public class WeatherDataImportService {

    private static final Logger log = LoggerFactory.getLogger(WeatherDataImportService.class);
    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherProperties weatherProperties;

    @Autowired
    public WeatherDataImportService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository, WeatherProperties weatherProperties) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
        this.weatherProperties = weatherProperties;
    }


    /**
     * Scheduled task to fetch and save weather data.
     * Fetches weather data XML from an external API, processes it, and saves it to the database.
     * Runs every hour at 15 minutes past the hour.
     */
    @Scheduled(cron = "0 15 * * * *") // This runs at 15 minutes past every hour
    public void fetchAndSaveWeatherData() {
        log.info("Starting to fetch weather data...");
        String xmlData = fetchWeatherDataXml();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Observations observations = (Observations) unmarshaller.unmarshal(new StringReader(xmlData));

            List<String> stations = weatherProperties.getStations();

            observations.getStations().stream()
                    .filter(station -> stations.contains(station.getName()))
                    .forEach(this::processAndSaveStationData);

            log.info("Weather data fetched and saved successfully.");
        } catch (JAXBException e) {
            log.error("Failed to process weather data XML", e);
            throw new WeatherDataProcessingException("Failed to process weather data XML", e);
        }
    }

    // Helper method to fetch weather data XML from external API
    private String fetchWeatherDataXml() {
        String url = "https://ilmateenistus.ee/ilma_andmed/xml/observations.php";
        return restTemplate.getForObject(url, String.class);
    }

    // Helper method to process and save station data to the database
    private void processAndSaveStationData(StationData stationData) {
        WeatherData weatherData = new WeatherData();
        weatherData.setStationName(stationData.getName());
        weatherData.setWmoCode(stationData.getWmoCode());
        weatherData.setAirTemperature(stationData.getAirTemperature());
        weatherData.setWindSpeed(stationData.getWindSpeed());
        weatherData.setWeatherPhenomenon(stationData.getWeatherPhenomenon());
        weatherData.setTimestamp(LocalDateTime.now());

        weatherDataRepository.save(weatherData);
    }

}