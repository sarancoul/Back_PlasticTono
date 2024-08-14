package com.example.plasti_tono.Services;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
    private ScheduledFuture<?> futureTask;

    private PointsService pointsService;

    @Autowired
    public SessionService(SessionRepository sessionRepository, PointsService pointsService) {
        this.sessionRepository = sessionRepository;
        this.pointsService = pointsService;
    }

    ///:::::::::::::::::::/// demarer session apres scanne///////:::::::::::::::::::////////
    public Session demarrerSession(Utilisateurs utilisateurs){
        Session session = new Session();
        session.setUtilisateur(utilisateurs);
        session.setActive(true);
        session.setDatedebut(LocalDateTime.now());
        session = sessionRepository.save(session);

        ///////////////::::::::::Demarer un timer pour la fermeture automatique de la session après 10 secondes
       tempInactive(session.getIdSession());
        return  session;
    }

    ///////::::::::::::::::://////verifier quelle session appartient a quelle utilisateur/////////::::::::::::::::://///////////
    public List<Session> getSessionsByUtilisateur(Utilisateurs utilisateur) {
        return sessionRepository.findByUtilisateur(utilisateur);
    }

    /////////::::::::::::::::://///////cloturer une session//////////////:::::::::::::::::::://////////////////////////////:::

    public Session cloturerSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session non trouvée avec l'ID : " + sessionId));
        session.setActive(false);
        session.setDateFin(LocalDateTime.now());
        sessionRepository.save(session);
        //// si la session se termine manuellement, annuler le timer
        if (futureTask != null && !futureTask.isCancelled()){
            futureTask.cancel(true);
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

    public Points deposerDechets(Long sessionId, double poids) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session non trouvée avec l'ID : " + sessionId));

        // je verifie si la session est active
        if (!session.isActive()) {
            throw new IllegalStateException("Le dépôt ne peut pas être effectué car la session est expirée, veuillez-vous connectez a nouveau.");
        }

        // Si la session est active, on continue avec l'enregistrement des points
        Points points = pointsService.EnregistrePoint(session, poids);
        session.setLastActivityTime(LocalDateTime.now());
        sessionRepository.save(session);
        return points;
    }


    ///////////////::::: methode pour verification du timer //////////////////;;;;;;:::::::::::::///////////
    private void tempInactive(Long sessionId) {
        futureTask = taskScheduler.schedule(() -> {
            // Vérifie si la session est toujours active après 10 secondes
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new RuntimeException("Session non trouvée"));
            if (session.isActive()) {
                cloturerSession(sessionId); // Termine la session si elle est encore active
                System.out.println("Session terminée automatiquement après 10 secondes d'inactivité.");
            }
        }, LocalDateTime.now().plusSeconds(20).atZone(ZoneId.systemDefault()).toInstant());
    }



}
