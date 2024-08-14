package com.example.plasti_tono.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idNotification;

    @NotBlank
    @Size(min =2, max = 80, message = "Ecrivez un titre court")
    private String titre;

    @NotBlank
    private String message;

    private LocalDateTime dateNotification = LocalDateTime.now();

}
