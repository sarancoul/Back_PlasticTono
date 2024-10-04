package com.example.plasti_tono.Model;

import jakarta.persistence.*;
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
    @Size(min = 2, max = 200, message = "Ecrivez un titre compréhensif")
    private String titre;

    @NotBlank
    private String message;

    @NotBlank
    private String type;


    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateurs utilisateur;

    private LocalDateTime dateNotification = LocalDateTime.now();
}
