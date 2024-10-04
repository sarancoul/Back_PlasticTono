package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Exception.UtilisateurNonTrouveException;
import com.example.plasti_tono.FireConfig.FirestoreService;
import com.example.plasti_tono.Model.Notification;
import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.UtilisateurPointsDTO;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.NotificationRepository;
import com.example.plasti_tono.Services.PointsService;
import com.example.plasti_tono.Services.UtilisateursService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UtilisateursController {

    private final UtilisateursService utilisateursService;
    private final PointsService pointsService;

    private final NotificationRepository notificationRepository;



    @Autowired
    public UtilisateursController(UtilisateursService utilisateursService, PointsService pointsService, NotificationRepository notificationRepository) {
        this.utilisateursService = utilisateursService;
        this.pointsService = pointsService;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/read")
    public ResponseEntity<List<Utilisateurs>> getAllUtilisateurs() {
        List<Utilisateurs> utilisateurs = utilisateursService.getAllUtilisateurs();
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Utilisateurs> getUtilisateurById(@PathVariable Long id) {
        Utilisateurs utilisateur = utilisateursService.getUtilisateurById(id);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Utilisateurs> createUtilisateur(@RequestBody Utilisateurs utilisateur) {
        Utilisateurs newUtilisateur = utilisateursService.saveUtilisateur(utilisateur);
        return new ResponseEntity<>(newUtilisateur, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Utilisateurs> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateurs utilisateur) {
        Utilisateurs existingUtilisateur = utilisateursService.getUtilisateurById(id);
        if (existingUtilisateur != null) {
            utilisateur.setIdUtilisateur(id);
            Utilisateurs updatedUtilisateur = utilisateursService.saveUtilisateur(utilisateur);
            return new ResponseEntity<>(updatedUtilisateur, HttpStatus.OK);
        } else {
            throw new UtilisateurNonTrouveException("Utilisateur non trouvé avec l'ID : " + id);
        }
    }

    @GetMapping("/searchBynomprenom")
    public ResponseEntity<Utilisateurs> getUtilisateurBynomandprenom(@RequestParam String nom, @RequestParam String prenom) {
        Utilisateurs utilisateur = utilisateursService.getUtilisateurBynomandprenom(nom, prenom);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @GetMapping("/searchByNumTel")
    public ResponseEntity<Utilisateurs> getUtilisateurByNumTel(@RequestParam String numTel) {
        Utilisateurs utilisateur = utilisateursService.getUtilisateurByNumTel(numTel);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Utilisateurs> connectionUtilisateur(@RequestParam("nom") String nom,
                                                              @RequestParam("prenom") String prenom,
                                                              @RequestParam("numTel") String numTel) {
        Utilisateurs utilisateur = utilisateursService.connectionUtilisateur(nom, prenom, numTel);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Utilisateurs>> searchUtilisateurs(@RequestParam String query) {
        List<Utilisateurs> utilisateurs = utilisateursService.searchByNomOrPrenomOrNumTel(query);
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }


   /* @GetMapping("/read")
    public ResponseEntity<List<UtilisateurPointsDTO>> getAllUtilisateursWithPoints() {
        List<Utilisateurs> utilisateurs = utilisateursService.getAllUtilisateurs();
        List<UtilisateurPointsDTO> utilisateurPointsList = new ArrayList<>();

        for (Utilisateurs utilisateur : utilisateurs) {
            // Récupérer les points en Double depuis le service points
            Double points = pointsService.getPointsByUserId(utilisateur.);

            // Créer le DTO avec les informations nécessaires
            UtilisateurPointsDTO utilisateurPointsDTO = new UtilisateurPointsDTO(
                    utilisateur.getName(),
                    utilisateur.getPhone(),
                    points
            );

            utilisateurPointsList.add(utilisateurPointsDTO);
        }

        return new ResponseEntity<>(utilisateurPointsList, HttpStatus.OK);
    }*/

    @GetMapping("/read/points")
    public ResponseEntity<List<UtilisateurPointsDTO>> getAllUtilisateursWithPoints() {
        List<Utilisateurs> utilisateurs = utilisateursService.getAllUtilisateurs();
        List<UtilisateurPointsDTO> utilisateurPointsDTOList = new ArrayList<>();

        for (Utilisateurs utilisateur : utilisateurs) {
            Double points = pointsService.getPointsByUserId(utilisateur.getIdUtilisateur());
            UtilisateurPointsDTO utilisateurPointsDTO = new UtilisateurPointsDTO(
                    utilisateur.getNom(),
                    utilisateur.getNumTel(),
                    points
            );
            utilisateurPointsDTOList.add(utilisateurPointsDTO);
        }

        return new ResponseEntity<>(utilisateurPointsDTOList, HttpStatus.OK);
    }
        @PostMapping("/envoyer/notif")
        public String envoyerNotification(
                @RequestParam String titre,
                @RequestParam String message,
                @RequestParam String type,
                @RequestParam String numTel) {

            try {
                utilisateursService.envoyerNotification(titre, message, type, numTel);
                return "Notification envoyée avec succès!";
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }
        }
        @GetMapping("/notif")
       public  List<Notification> getAllNotifications(){
        return notificationRepository.findAll();
        }

    @GetMapping("/api/notif")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@RequestParam String numTel) {
        List<Notification> notifications = notificationRepository.findByUtilisateurNumTel(numTel);
        return ResponseEntity.ok(notifications);
    }




}




