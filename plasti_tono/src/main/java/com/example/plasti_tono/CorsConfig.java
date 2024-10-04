package com.example.plasti_tono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                // Remplacer "api/**" par "/**" pour autoriser toutes les routes API si besoin
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "http://192.168.43.99:3000") // Suppression des guillemets en trop
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("*");
            }
        };
    }
}
