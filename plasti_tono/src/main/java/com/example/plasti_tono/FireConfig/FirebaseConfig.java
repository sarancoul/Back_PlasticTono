package com.example.plasti_tono.FireConfig;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FirebaseConfig {
    @Bean
    public Firestore getDb() {
        return FirestoreClient.getFirestore();
    }

}
