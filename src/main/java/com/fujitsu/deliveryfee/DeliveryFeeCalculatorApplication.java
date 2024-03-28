package com.fujitsu.deliveryfee;

import com.fujitsu.deliveryfee.config.WeatherProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;



/**
 * The main entry point for the Delivery Fee Calculator application.
 * This class initializes the Spring Boot application context and enables scheduling for weather data import.
 * It also configures a RestTemplate bean for making HTTP requests.
 */
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(WeatherProperties.class)
public class DeliveryFeeCalculatorApplication {

	/**
	 * Main method to start the Spring Boot application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
	}

	/**
	 * Creates a RestTemplate bean to be used for making HTTP requests.
	 *
	 * @return the configured RestTemplate bean
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
