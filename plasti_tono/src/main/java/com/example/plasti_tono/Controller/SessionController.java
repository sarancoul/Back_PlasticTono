package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Services.SessionService;
import com.example.plasti_tono.Services.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    private UtilisateursService utilisateursService;

    @Autowired
    public SessionController(SessionService sessionService, UtilisateursService utilisateursService) {
        this.sessionService = sessionService;
        this.utilisateursService = utilisateursService;

    }

    ///////////##############demarer une session################///////////////
    @PostMapping("/start")
    public ResponseEntity<Session> demarrerSession(@RequestBody Utilisateurs utilisateurs){
        Session newSession = sessionService.demarrerSession(utilisateurs);
        return new ResponseEntity<>(newSession, HttpStatus.CREATED);
    }

    ///////////##############récupérer les  sessions d'un utilisateur spécifique################///////////////
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Session>> getSessionsByUtilisateur(@PathVariable Long userId) {
        Utilisateurs utilisateur = utilisateursService.getUtilisateurById(userId);
        List<Session> sessions = sessionService.getSessionsByUtilisateur(utilisateur);
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    ////////////::::::::::APi pour deposer un dechets//////////:::::::::::://////////////////
    @PostMapping("/deposer/{sessionId}")
    public ResponseEntity<?> deposerDechets(@PathVariable Long sessionId, @RequestParam double poids) {
        try {
            Points points = sessionService.deposerDechets(sessionId, poids);
            return new ResponseEntity<>(points, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    ///////////:::::::::###########Api pour prolonger la session :::::::#########////////////////

    @PutMapping("/prolonger/{sessionId}")
    public ResponseEntity<Session> prolongerSession(@PathVariable Long sessionId) {
        Session updatedSession = sessionService.prolongerSession(sessionId);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    //////////////:::::::::::::cloturer une session avec son Id ::::::::::::::::///////////////////////
    @PutMapping("/cloturer/{sessionId}")
    public ResponseEntity<String> cloturerSession(@PathVariable Long sessionId) {
        Session session = sessionService.cloturerSession(sessionId);  // Récupérer la session après la clôture
        return new ResponseEntity<>("Session cloturée avec succès. Points cumulés: " + session.getTotalPoints(), HttpStatus.OK);
    }

}
