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


@Service
public class WeatherDataImportService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataImportService.class);
    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;
    private final WeatherProperties weatherProperties;

    @Autowired
    public WeatherDataImportService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository, WeatherProperties weatherProperties) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
        this.weatherProperties = weatherProperties;
    }

    @Scheduled(cron = "0 * * * * *") // This runs at 15 minutes past every hour CHANGE BACK TO @Scheduled(cron = "0 15 * * * *") WHEN FINISHED TESTING
    public void fetchAndSaveWeatherData() {
        logger.info("Starting to fetch weather data...");
        String xmlData = fetchWeatherDataXml();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Observations.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Observations observations = (Observations) unmarshaller.unmarshal(new StringReader(xmlData));

            List<String> stations = weatherProperties.getStations();

            observations.getStations().stream()
                    .filter(station -> stations.contains(station.getName()))
                    .forEach(this::processAndSaveStationData);

            logger.info("Weather data fetched and saved successfully.");
        } catch (JAXBException e) {
            logger.error("Failed to process weather data XML", e);
            throw new WeatherDataProcessingException("Failed to process weather data XML", e);
        }
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
        weatherData.setWindSpeed(stationData.getWindSpeed());
        weatherData.setWeatherPhenomenon(stationData.getWeatherPhenomenon());
        weatherData.setTimestamp(LocalDateTime.now());

        weatherDataRepository.save(weatherData);
    }

}