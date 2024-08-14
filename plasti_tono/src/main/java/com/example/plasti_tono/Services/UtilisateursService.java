package com.example.plasti_tono.Services;

import com.example.plasti_tono.Exception.UtilisateurNonTrouveException;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.UtilisateursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateursService {

    private UtilisateursRepository utilisateursRepository;
    @Autowired
    public UtilisateursService(UtilisateursRepository utilisateursRepository) {
        this.utilisateursRepository = utilisateursRepository;
    }

////////////////////////////////////Liste utilisateurs//////////////////////////////

    public List<Utilisateurs> getAllUtilisateurs() {
        return utilisateursRepository.findAll();
    }
//////////////////////////////////// Affichage d'un utilisateur//////////////////////////////

    public Utilisateurs getUtilisateurById(Long id) {
        return utilisateursRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNonTrouveException("Utilisateur non trouvé avec l'ID : " + id));
    }
//////////////////////////////////// recuperer par son nom et prenom//////////////////////////////

public Utilisateurs getUtilisateurBynomandprenom(String nom, String prenom) {
       Utilisateurs user = utilisateursRepository.findByNomAndPrenom(nom, prenom);
       if (user == null){
           throw new EntityNotFoundException("Utilisateur n'existe pas :" +nom +prenom);
       }
       return user;
}

//////////////////////////////////// recuperer par son num de telephone//////////////////////////////
    public  Utilisateurs getUtilisateurByNumTel(String numTel){
        Utilisateurs user = utilisateursRepository.findByNumTel(numTel);
        if (user == null){
            throw new EntityNotFoundException("Désolé utilisateur de ce numéro existe pas :" +numTel);
        }
        return user;
    }
    //////////////////////////////////// Sauvegarder un user //////////////////////////////
    public Utilisateurs saveUtilisateur(Utilisateurs utilisateur) {
        return utilisateursRepository.save(utilisateur);
    }
    //////////////////////////////////// verifier l'authentification //////////////////////////////
    public Utilisateurs connectionUtilisateur(String nom, String prenom, String numTel) {
        Utilisateurs user = utilisateursRepository.findByNomAndPrenomAndNumTel(nom, prenom, numTel);
        if (user == null) {
            throw new EntityNotFoundException("Cet utilisateur n'existe pas");
        }

        return user;
    }

    //////////////////////////////////// Modifier user //////////////////////////////


}
