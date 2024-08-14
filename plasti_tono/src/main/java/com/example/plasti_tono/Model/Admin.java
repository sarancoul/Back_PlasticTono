package com.example.plasti_tono.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdmin;

    @NotBlank
    @Size(min = 2, max = 60, message = "veuillez verifier votre nom")
    private String nom;

    @NotBlank
    @Column(name = "motdepasse")
    private String motDePasse;

    @OneToMany(mappedBy = "admin")
    private Set<Utilisateurs> utilisateurs;

    @OneToMany(mappedBy = "admin")
    private Set<Kiosque> kiosques;

    @ManyToMany
    @JoinTable(
            name = "admin_notif",
            joinColumns = @JoinColumn(name = "idAdmin"),
            inverseJoinColumns = @JoinColumn(name = "idNotification"))
    private Set<Notification> notifications;

    @ManyToMany
    @JoinTable(
            name = "admin_alarme",
            joinColumns = @JoinColumn(name = "idAdmin"),
            inverseJoinColumns = @JoinColumn(name = "idNotification"))
    private Set<Alarme> alarme;

}
