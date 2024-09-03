package com.example.plasti_tono;

import com.example.plasti_tono.ConfigMqtt.MqttService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.IOException;

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

	@PostConstruct
	public void initialize() throws IOException {
		FileInputStream serviceAccount = null;
		try {
			serviceAccount =
					new FileInputStream("serviceAccountKey.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://plastictono-e26cd-default-rtdb.firebaseio.com")
					.build();

			FirebaseApp.initializeApp(options);
		}catch (Exception e){
			System.out.println("----------------------eroorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		}

		//FileInputStream serviceAccount =
		//      new FileInputStream("serviceAccountKey.json");


	}
}
