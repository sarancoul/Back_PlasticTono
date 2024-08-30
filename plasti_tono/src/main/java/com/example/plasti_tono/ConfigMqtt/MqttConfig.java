package com.example.plasti_tono.ConfigMqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client.connect(options);


        System.out.println("Vous êtes connecter au broker: tcp://localhost:1883");

        return client;
    }


}
