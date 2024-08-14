package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Exception.UtilisateurNonTrouveException;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Services.UtilisateursService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UtilisateursController {
    private final UtilisateursService utilisateursService;

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
}




