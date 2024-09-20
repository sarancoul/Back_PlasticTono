package com.example.plasti_tono.Services;

import com.example.plasti_tono.FireConfig.FirebaseService;
import com.example.plasti_tono.FireConfig.FirestoreService;
import com.example.plasti_tono.Model.Kiosque;
import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
    private ScheduledFuture<?> futureTask;

    private PointsRepository pointsRepository;

    private UtilisateursRepository utilisateursRepository;

    private  KiosqueRepository kiosqueRepository;

    private FirestoreService firestoreService;

    private PointTotalService pointTotalService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, PointsRepository pointsRepository, KiosqueRepository kiosqueRepository, UtilisateursRepository utilisateursRepository, FirestoreService firestoreService, PointTotalService pointTotalService) {
        this.sessionRepository = sessionRepository;
        this.kiosqueRepository = kiosqueRepository;
        this.utilisateursRepository = utilisateursRepository;
        this.firestoreService = firestoreService;
        this.pointsRepository=pointsRepository;
        this.pointTotalService=pointTotalService;

    }
    ///:::::::::::::::::::/// demarer session apres scanne///////:::::::::::::::::::////////
    public Session demarrerSession(Utilisateurs utilisateurs, String kioskCode, Double poids) {
        Kiosque kiosque = kiosqueRepository.findByCode(kioskCode)
                .orElseThrow(() -> new EntityNotFoundException("Kiosque non trouvé avec le code: " + kioskCode));

        Utilisateurs utilisateurs1=utilisateursRepository.findByNumTel(utilisateurs.getNumTel());
        if (utilisateurs1==null) utilisateurs=utilisateursRepository.save(utilisateurs);
        else utilisateurs=utilisateurs1;

        Session session = new Session();
        session.setUtilisateur(utilisateurs);
        session.setKiosque(kiosque);
        session.setActive(true);
        session.setDatedebut(LocalDateTime.now());
        session.setPoids((Double) poids);
        System.out.println("Tentative de sauvegarde dans phpmyadmin");
        session = sessionRepository.save(session);
        System.out.println("Enregistré avec succes avc id dans php admin: " + session.getIdSession());
        System.out.println("Poids lors de la création de la session: " + session.getPoids());

        //Enregistrer la session dans firebase
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("session_id",session.getIdSession());
        sessionData.put("userId", utilisateurs.getIdUtilisateur());
        sessionData.put("kioskCode", kioskCode);
        sessionData.put("active", true);
        sessionData.put("startTime", session.getDatedebut().toString());
        sessionData.put("poids", poids);

        // Ici, on enregistre dans Firestore
        firestoreService.saveSessionData(session.getIdSession() +"-"+kioskCode, sessionData);

        // Démarrer un timer pour la fermeture automatique de la session après 10 secondes
        tempInactive(session.getIdSession());

        // Renvoyer l'ID de session et d'autres informations si nécessaire
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getIdSession());  // Assurez-vous que l'ID de session est renvoyé ici
        response.put("message", "Session démarrée avec succès");
        return session;
    }

    ///////::::::::::::::::://////verifier quelle session appartient a quelle utilisateur/////////::::::::::::::::://///////////
    public List<Session> getSessionsByUtilisateur(Utilisateurs utilisateur) {
        return sessionRepository.findByUtilisateur(utilisateur);
    }

    /////////::::::::::::::::://///////cloturer une session//////////////:::::::::::::::::::://////////////////////////////:::

   public Session cloturerSession(Long sessionId) {
        // Récupérer la session depuis la base de données
        Session session = sessionRepository.findFirstByIdSession(sessionId);
        if (session==null) {
            System.out.println("-------------------------SESSION::::" + null);

            throw new EntityNotFoundException("Session non trouvée avec l'Id correspondant: " + sessionId);
            //.orElseThrow(() -> new EntityNotFoundException("Session non trouvée avec l'Id correspondant: " + sessionId));
        }
       System.out.println("-------------------------SESSION::::" + session);
       System.out.println("-------------------------sessionId::::"+sessionId);
       System.out.println("-------------------------SESSION::::" + session.getIdSession());
       System.out.println("-------------------------SESSION::::" + session.getPoids());
       System.out.println("-------------------------SESSION::::" + session.getKiosque().getCode());


       /*int currentPoids = session.getPoids();
       System.out.println("Poids actuel récupéré: " + currentPoids);*/

        // Mettre à jour l'état de la session
        session.setActive(false);
        session.setDateFin(LocalDateTime.now());
       // session.setPoids(50);

       // Mettre à jour la session dans Firestore
        Map<String, Object> sessionUpdate = new HashMap<>();
        sessionUpdate.put("active", false);
        sessionUpdate.put("session_id",session.getIdSession());
        sessionUpdate.put("startTime", session.getDatedebut().toString());
        sessionUpdate.put("endTime", session.getDateFin().toString());

       //firestoreService.deleteSessionData(session.getIdSession() + "-" + session.getKiosque().getCode());   /////////supriimmmmmmmmmerrrrrrrrr


       String sessionUId=sessionId+"-"+session.getKiosque().getCode();

       session.setFirebase_uid(sessionUId);
       session = sessionRepository.save(session);

       System.out.println("-------------------------SESSIONuid::::" + sessionUId);
       System.out.println("-------------------------SESSIONuid from object::::" + session.getFirebase_uid());

       //get firebase session
       Map<String,Object> res=firestoreService.getSessionData(sessionUId);
       System.out.println("-------session recupere chez firebase::::"+res);

       System.out.println("-------poid recupere chez firebase::::"+res.get("poids"));

       Double poid=  asDouble(res.get("poids"));

       System.out.println("-------poid22 recupere chez firebase::::"+ res.get("poids"));

       session.setPoids(poid);
       sessionUpdate.put("poids", session.getPoids());
       Session session1= sessionRepository.save(session);

       System.out.println(session);

       System.out.println(session1);



       try {
            firestoreService.updateSessionData(sessionUId, sessionUpdate);
            System.out.println("Mise à jour de Firestore réussie.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de Firestore: " + e.getMessage());
            // Vous pouvez aussi lancer une exception ou enregistrer cette erreur dans un journal
        }

        // Annuler le timer si la session est terminée manuellement
        if (futureTask != null && !futureTask.isCancelled()) {
            futureTask.cancel(true);
            System.out.println("Timer annulé.");
        }
        return session;
    }

    /////////////////////////////:::::prolonger la session si l'utilisateur repond non au front pour prolonger la session::::::::::://////////////
    public Session prolongerSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session non trouvée avec l'ID : " + sessionId));
        session.setLastActivityTime(LocalDateTime.now());  // Mise à jour du temps de la dernière activité
        sessionRepository.save(session);

        // Reprogrammer le timer pour 10 secondes supplémentaires
        tempInactive(sessionId);

        return session;
    }
    //////////:::::::::::depots dechets::::::::::::://///////////////////////////////

   /* public Points deposerDechets(Long sessionId, double poids) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session non trouvé avec l'Id correspondant: " + sessionId));

        // Vérifie si la session est active
        if (!session.isActive()) {
            throw new IllegalStateException("Le dépot ne peut être faites car la session est terminé. merci de vous connecter.");
        }

        // Si la session est active, on continue avec l'enregistrement des points
        Points points = pointsService.EnregistrePoint(session, poids);

        // Ajouter les points au total de la session
        int pointsGagnes = points.getPoints();
        session.setTotalPoints(session.getTotalPoints() + pointsGagnes);

        // Met à jour le temps de la dernière activité
        session.setLastActivityTime(LocalDateTime.now());
        sessionRepository.save(session);

        return points;
    }
*/
    ///////////////::::: methode pour verification du timer //////////////////;;;;;;:::::::::::::///////////
    private void tempInactive(Long sessionId) {
        futureTask = taskScheduler.schedule(() -> {
            // Vérifie si la session est toujours active après 10 secondes
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new RuntimeException("Session non trouvée"));
            if (session.isActive()) {
                cloturerSession(sessionId); // Termine la session si elle est encore active
                System.out.println("Session terminée automatiquement après 1 minutes d'inactivité");
            }
        }, LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault()).toInstant());
    }

    Double asDouble(Object o) {
        Double val = null;
        if (o instanceof Number) {
            val = ((Number) o).doubleValue();
        }
        return val;
    }


}
