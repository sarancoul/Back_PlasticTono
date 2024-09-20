package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Model.PointTotal;
import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Repository.PointsRepository;
import com.example.plasti_tono.Repository.SessionRepository;
import com.example.plasti_tono.Services.PointTotalService;
import com.example.plasti_tono.Services.PointsService;
import com.example.plasti_tono.Services.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointsController {
    @Autowired
    private UtilisateursService utilisateursService;
    private SessionRepository sessionRepository;
    private PointsRepository pointsRepository;
    private PointsService pointsService;

    private PointTotalService pointTotalService;

    public PointsController(UtilisateursService utilisateursService, SessionRepository sessionRepository, PointsRepository pointsRepository, PointsService pointsService,PointTotalService pointTotalService) {
        this.utilisateursService = utilisateursService;
        this.sessionRepository = sessionRepository;
        this.pointsRepository = pointsRepository;
        this.pointsService = pointsService;
        this.pointTotalService=pointTotalService;
    }

    /*@GetMapping("/total/{firebaseUid}")
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
    }*/

    @GetMapping("/total/{firebaseUid}")
    public ResponseEntity<Double> getTotalPointsByFirebaseUid(@PathVariable String firebaseUid){
        final  var utilisateur = utilisateursService.fetchUtilisateursByFirebaseUUID(firebaseUid);
        if (utilisateur.isPresent()){
            PointTotal p= pointTotalService.getPointTotal(utilisateur.get());
            if (p==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
            }else return  ResponseEntity.ok(p.getPoint());
            /*List<Session> sessions = sessionRepository.findByUtilisateur_IdUtilisateur(utilisateur.get().getIdUtilisateur());
            if (sessions.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(0.0);
            }
            double totalPoints = 0;

            for (Session session : sessions){
                Points points = pointsRepository.findFirstBySession(session);
                totalPoints += (points != null) ? points.getPoints() : 0;
            }
            return  ResponseEntity.ok(totalPoints);*/
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(0.0);
        }
    }


    /*@PostMapping("/convert")
    public ResponseEntity<Double> convertirPointsEnArgent(@RequestBody Points request) {
        Double argent = pointsService.convertirPointsEnArgent(request.getPoints());
        return ResponseEntity.ok(argent);
    }*/
    @PostMapping("/convert")
    public ResponseEntity<Double> convertirPointsEnArgent(@RequestBody Points request,@RequestParam(name = "userId") String firebaseUid) {
        final  var utilisateur = utilisateursService.fetchUtilisateursByFirebaseUUID(firebaseUid);

        Double argent = pointsService.convertirPointsEnArgent(request.getPoints());

        System.out.println("------------------------------------------------------argent");
        System.out.println(argent);

        System.out.println("------------------------------------------------------point");
        System.out.println(request.getPoints());

        pointTotalService.saveOrUpdatePoint(utilisateur.get(),-request.getPoints());

        /*Session session = sessionRepository.findById(request.getSession().getIdSession())
               .orElseThrow(() -> new RuntimeException("Session non trouvée"));

        /System.out.println("------------------------------------------------------session");
        System.out.println(session.getIdSession());
        Points points = pointsRepository.findFirstBySession(session);

        if (points != null) {

            double newTotalPoints = points.getPoints() - request.getPoints();
            points.setPoints(newTotalPoints);
            // Sauvegarder les nouveaux points
            pointsRepository.save(points);
        }*/

        return ResponseEntity.ok(argent);
    }

}
