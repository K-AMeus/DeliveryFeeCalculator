package com.fujitsu.deliveryfee;

import com.fujitsu.deliveryfee.config.WeatherProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(WeatherProperties.class)
public class DeliveryFeeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryFeeCalculatorApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
