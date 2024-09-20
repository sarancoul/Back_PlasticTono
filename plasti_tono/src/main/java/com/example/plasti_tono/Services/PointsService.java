package com.example.plasti_tono.Services;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.PointsRepository;
import com.example.plasti_tono.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsService {
    private  final PointsRepository pointsRepository;
    private SessionRepository sessionRepository;

    private final double conversionRate = 10.0;

    @Autowired
    public PointsService(PointsRepository pointsRepository, SessionRepository sessionRepository) {
        this.pointsRepository = pointsRepository;
        this.sessionRepository=sessionRepository;
    }

    public Points EnregistrePoint(Session session, double poids){

        Double points = calculerPoints(poids);
        Points nouveauPoints = new Points();
        nouveauPoints.setPoids(poids);
        nouveauPoints.setPoints(points);
        nouveauPoints.setSession(session);

        return pointsRepository.save(nouveauPoints);
    }
    public Double calculerPoints(double poids) {

        double points = poids / 65;

        return  points;
    }

    public  Points getByIdSession(Long id){
        return pointsRepository.findFirstBySessionIdSession(id);
    }


    public Double convertirPointsEnArgent(Double points) {
        return points * conversionRate;
    }


    /*private int calculerPoints(double poids) {



        if (poids >= 100) {
            return 200; // 100 kg ou plus donne 200 points
        } else if (poids >= 75) {
            return 150; // 75 kg ou plus donne 150 points
        } else if (poids >= 50) {
            return 100; // 50 kg ou plus donne 100 points
        } else if (poids >= 25) {
            return 50; // 25 kg ou plus donne 50 points
        } else if (poids >= 10) {
            return 25; // 10 kg ou plus donne 25 points
        } else if (poids >= 5) {
            return 10; // 5 kg ou plus donne 10 points
        } else if (poids >= 1) {
            return 2; // 1 kg ou plus donne 2 points
        } else {
            return 0; // Moins de 1 kg ne donne pas de points
        }
    }*/

        ////////////////recevoir points de user connecté///////////////////////////////
    /* public  int calculerTotalPointsUtilisateur(Long idUtilisateur){
        List<Session> sessions = sessionRepository.findByUtilisateur_IdUtilisateur(idUtilisateur);
        int totalPoints = 0;
        for (Session session : sessions){
            Points points = pointsRepository.findBySession(session.getIdSession());
            totalPoints += points.getPoints();
        }
        return totalPoints;
     }*/

    }
