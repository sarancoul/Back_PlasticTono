package com.example.plasti_tono.Services;

import com.example.plasti_tono.Exception.UtilisateurNonTrouveException;
import com.example.plasti_tono.Model.Notification;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.NotificationRepository;
import com.example.plasti_tono.Repository.UtilisateursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateursService {

    private UtilisateursRepository utilisateursRepository;
    private NotificationRepository notificationRepository;
    @Autowired
    public UtilisateursService(UtilisateursRepository utilisateursRepository, NotificationRepository notificationRepository) {
        this.utilisateursRepository = utilisateursRepository;
        this.notificationRepository = notificationRepository;
    }

////////////////////////////////////Liste utilisateurs//////////////////////////////

    public List<Utilisateurs> getAllUtilisateurs() {
        return utilisateursRepository.findAll();
    }

//////////////////////////////////// Affichage d'un utilisateur//////////////////////////////
    public Optional<Utilisateurs> fetchUtilisateursByFirebaseUUID(String firebaseUUID){
        return utilisateursRepository.findByFirebaseUid(firebaseUUID);
    }
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

    public List<Utilisateurs> searchByNomOrPrenomOrNumTel(String query) {
        return utilisateursRepository.findByNomContainingOrPrenomContainingOrNumTelContaining(query, query, query);
    }

    public void envoyerNotification(String titre, String message, String type, String numTel) {
        Utilisateurs utilisateur = utilisateursRepository.findByNumTel(numTel);

        if (utilisateur != null) {
            Notification notification = new Notification();
            notification.setTitre(titre);
            notification.setMessage(message);
            notification.setType(type);
            notification.setUtilisateur(utilisateur);
            notificationRepository.save(notification);
        } else {
            throw new IllegalArgumentException("Utilisateur avec ce numéro de téléphone n'existe pas");
        }
    }


    //////////////////////////////////// Modifier user //////////////////////////////


}
