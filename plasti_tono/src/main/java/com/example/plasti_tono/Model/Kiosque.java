package com.example.plasti_tono.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "kiosque")
    private Set<Tas_Dechets> tasDechets;
    @JsonIgnore
    @OneToMany(mappedBy = "kiosque")
    private Set<Alarme> alarme;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Admin admin;
}
