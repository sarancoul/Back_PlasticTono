package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Repository.PointsRepository;
import com.example.plasti_tono.Repository.SessionRepository;
import com.example.plasti_tono.Services.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointsController {
    @Autowired
    private UtilisateursService utilisateursService;
    private SessionRepository sessionRepository;
    private PointsRepository pointsRepository;

    public PointsController(UtilisateursService utilisateursService, SessionRepository sessionRepository, PointsRepository pointsRepository) {
        this.utilisateursService = utilisateursService;
        this.sessionRepository = sessionRepository;
        this.pointsRepository = pointsRepository;
    }

    @GetMapping("/total/{firebaseUid}")
    public ResponseEntity<Double> getTotalPointsByFirebaseUid(@PathVariable String firebaseUid){
        final  var utilisateur = utilisateursService.fetchUtilisateursByFirebaseUUID(firebaseUid);
        if (utilisateur.isPresent()){
            List<Session> sessions = sessionRepository.findByUtilisateur_IdUtilisateur(utilisateur.get().getIdUtilisateur());
             if (sessions.isEmpty()){
                 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(0.0);
             }
             double totalPoints = 0;

             for (Session session : sessions){
                 Points points = pointsRepository.findFirstBySession(session);
                 totalPoints += (points != null) ? points.getPoints() : 0;

             }
             return  ResponseEntity.ok(totalPoints);
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
        }
    }
}
