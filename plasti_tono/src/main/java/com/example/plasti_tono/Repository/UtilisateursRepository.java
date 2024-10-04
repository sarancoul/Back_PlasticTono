package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {
    public Utilisateurs findByNomAndPrenom(String nom, String prenom);  // Correction ici
    public Utilisateurs findByNumTel(String numTel);
    public Utilisateurs findByNomAndPrenomAndNumTel(String nom, String prenom, String numTel);

    public Optional<Utilisateurs> findByFirebaseUid(String firebaseUid);

    List<Utilisateurs> findByNomContainingOrPrenomContainingOrNumTelContaining(String nom, String prenom, String numTel);

}
