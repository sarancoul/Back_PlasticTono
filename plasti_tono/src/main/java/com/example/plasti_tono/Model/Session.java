package com.example.plasti_tono.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSession;


    private LocalDateTime datedebut = LocalDateTime.now();

    private LocalDateTime dateFin;

    private LocalDateTime lastActivityTime;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;

    @JsonIgnore
    @OneToMany(mappedBy = "session")
    private Set<Points> points;

}
