package com.example.plasti_tono.ConfigMqtt;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MqttService {
    @Autowired
    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        try {
            // Souscription au topic "test/topic" lors du démarrage de l'application
            subscribe("test/topic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String payload) throws Exception{
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        mqttClient.publish(topic,message);

    }

    public void subscribe(String topic) throws Exception {
        mqttClient.subscribe(topic, (t, msg) -> {
            String message = new String(msg.getPayload());
            System.out.println("Message reçu : " + t);
            System.out.println("Message reçu : " + message);

        });
    }

}
