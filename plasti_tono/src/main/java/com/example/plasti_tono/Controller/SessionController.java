package com.example.plasti_tono.Controller;

import com.example.plasti_tono.FireConfig.FirebaseService;
import com.example.plasti_tono.FireConfig.FirestoreService;
import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.UtilisateursRepository;
import com.example.plasti_tono.Services.SessionService;
import com.example.plasti_tono.Services.UtilisateursService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private FirebaseService firebaseService ;
    private SessionService sessionService;
    private UtilisateursService utilisateursService;
    private UtilisateursRepository utilisateursRepositry;
    FirestoreService firestoreService;

    @Autowired
    public SessionController(SessionService sessionService, UtilisateursService utilisateursService, UtilisateursRepository utilisateursRepository, FirebaseService firebaseService,FirestoreService firestoreService) {
        this.sessionService = sessionService;
        this.utilisateursService = utilisateursService;
        this.utilisateursRepositry = utilisateursRepository;
        this.firebaseService = firebaseService;
        this.firestoreService=firestoreService;
    }
    ///////////##############demarer une session################///////////////
    @PostMapping("/start")
    public ResponseEntity<Session> demarrerSession(
            HttpServletRequest request,
            @RequestParam String kioskCode) {
        try {
            String token = request.getHeader("Authorization");

            System.out.println("---------------{}::::::::::::::::::::::::"+token);
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            System.out.println("---------------222222::::{}"+token);
            FirebaseToken firebaseToken = firebaseService.verifyToken(token);
            System.out.println("---------------firetoken::::{}"+firebaseToken);
            String uid = firebaseToken.getUid();
            System.out.println("---------------uid::::{}"+uid);
            Map<String,Object> object = firestoreService.getUserData(uid);
            Utilisateurs utilisateur= new Utilisateurs(object);
            System.out.println("---------------utilisateur::::{}"+utilisateur);
            if (utilisateur == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Session newSession = sessionService.demarrerSession(utilisateur, kioskCode);
            System.out.println("---------------session::::{}"+newSession);
            return ResponseEntity.ok(newSession);
        } catch (Exception e) {
            System.out.println("---------------exception::::{}"+e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
