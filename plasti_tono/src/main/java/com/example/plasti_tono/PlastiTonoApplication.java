package com.example.plasti_tono;

import com.example.plasti_tono.ConfigMqtt.MqttService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlastiTonoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlastiTonoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {
			MqttService mqttService = appContext.getBean(MqttService.class);
			try {
				mqttService.subscribe("#");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
}
