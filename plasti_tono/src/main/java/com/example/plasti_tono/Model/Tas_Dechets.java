package com.example.plasti_tono.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.IdGeneratorType;

import java.time.LocalDateTime;

@Data
@Entity
public class Tas_Dechets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long idTas_dechet;

    private int poids;

    //private LocalDateTime datedepot= LocalDateTime.now();
    /// calcule des poinds
    //type de plastique


    @ManyToOne
    @JoinColumn(name = "idUtilisateurs")
    private Utilisateurs utilisateur;

    @ManyToOne
    @JoinColumn(name = "idKiosque")
    private Kiosque kiosque;

}
