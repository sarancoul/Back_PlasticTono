package com.example.plasti_tono.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Utilisateurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUtilisateur;

    @NotBlank
    @Size(min = 2, max = 80, message = "Nombre de caractère invalide")
    private String nom;

    @NotBlank
    @Size(min = 2, max = 80, message = "Nombre de caractère invalide")
    private String prenom;

    @NotBlank
    @Size(min = 8, max = 12, message = "Nombre de caractère de chiffre invalid")
    private String numTel;

    private boolean active;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Session> sessions;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Tas_Dechets> tasDechets;

    @OneToMany(mappedBy = "utilisateur")
    private Set<Points> points;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Admin admin;

    @ManyToMany
    @JoinTable(
            name = "user_notif",
            joinColumns = @JoinColumn(name = "idUtilisateur"),
            inverseJoinColumns = @JoinColumn(name = "idNotification"))
    private Set<Notification> notifications;


}
