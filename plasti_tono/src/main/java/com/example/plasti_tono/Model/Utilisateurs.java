package com.example.plasti_tono.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUtilisateur;


    @Size(min = 2, max = 80, message = "Nombre de caractère invalide")
    private String nom;


    @Size(min = 2, max = 80, message = "Nombre de caractère invalide")
    private String prenom;

    @NotBlank
    @Size(min = 8, max = 12, message = "Nombre de caractère de chiffre invalid")
    private String numTel;

    private String firebaseUid;

    private String email;
    private boolean active;


    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private Set<Session> sessions;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private Set<Tas_Dechets> tasDechets;

    @JsonIgnore
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

    public Utilisateurs(Map<String,Object> map){
        System.out.println("------------------------"+map);
        this.nom=map.get("name").toString();
        this.numTel=map.get("phone").toString();
        this.firebaseUid=map.get("userId").toString();
        this.email=map.get("email").toString();

    }

}
