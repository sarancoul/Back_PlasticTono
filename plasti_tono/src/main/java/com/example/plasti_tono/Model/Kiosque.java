package com.example.plasti_tono.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Kiosque {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idKiosque;

    private String Localisation;

    private int niveau_remplissage;

    private String etat;

    @OneToMany(mappedBy = "kiosque")
    private Set<Tas_Dechets> tasDechets;

    @OneToMany(mappedBy = "kiosque")
    private Set<Alarme> alarme;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Admin admin;
}
