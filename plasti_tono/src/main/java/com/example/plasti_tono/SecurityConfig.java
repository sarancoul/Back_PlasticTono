package com.example.plasti_tono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour les WebSockets
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/ws/**").permitAll() // Autorise les connexions WebSocket
                        .requestMatchers("/mqtt/**").permitAll() // Autorise les connexions MQTT
                        .requestMatchers("/session/**").permitAll() // Autorise l'accès aux sessions
                        .requestMatchers("/user/**").permitAll() // Autorise l'accès public à /user/**
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Requiert le rôle ADMIN pour /admin/**
                        .requestMatchers("/kiosque/**").permitAll()
                        .requestMatchers("/firebase/**").permitAll()
                        .requestMatchers("/Suggestions/**").permitAll()
                        .anyRequest().permitAll()// Authentification requise pour toutes les autres requêtes
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
