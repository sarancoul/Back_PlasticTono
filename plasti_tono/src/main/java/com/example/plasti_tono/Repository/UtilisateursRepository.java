package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {
    public Utilisateurs findByNomAndPrenom(String nom, String prenom);  // Correction ici
    public Utilisateurs findByNumTel(String numTel);
    public Utilisateurs findByNomAndPrenomAndNumTel(String nom, String prenom, String numTel);

}
