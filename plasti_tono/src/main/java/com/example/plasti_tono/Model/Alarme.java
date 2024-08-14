package com.example.plasti_tono.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Alarme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAlarme;

    @NotBlank
    @Size(min = 2, max = 60, message = "Veuillez mettre un court tritre")
    private String titre;

    @NotBlank
    @Size(min = 2, max = 200, message = "veuillez message court")
    private String message;

    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "idKiosque")
    private Kiosque kiosque;

    @ManyToMany(mappedBy = "alarme")
    private Set<Admin> admins;

}
