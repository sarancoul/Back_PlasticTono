package com.example.plasti_tono.Services;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.TransactionHistorique;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.PointsRepository;
import com.example.plasti_tono.Repository.SessionRepository;
import com.example.plasti_tono.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PointsService {
    private  final PointsRepository pointsRepository;
    private SessionRepository sessionRepository;
    private final TransactionRepository transactionRepository;
    private final double conversionRate = 10.0;

    @Autowired
    public PointsService(PointsRepository pointsRepository, SessionRepository sessionRepository, TransactionRepository transactionRepository) {
        this.pointsRepository = pointsRepository;
        this.sessionRepository=sessionRepository;
        this.transactionRepository = transactionRepository;
    }

    public Points EnregistrePoint(Session session, double poids){

        Double points = calculerPoints(poids);
        Points nouveauPoints = new Points();
        nouveauPoints.setPoids(poids);
        nouveauPoints.setPoints(points);
        nouveauPoints.setSession(session);
        Utilisateurs utilisateur = session.getUtilisateur();
        nouveauPoints.setUtilisateur(utilisateur);

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

    public void enregistrerHistoriqueTransaction(Utilisateurs utilisateurs, Double points, Double argent) {

        TransactionHistorique transaction = new TransactionHistorique();
        transaction.setUserId(utilisateurs.getFirebaseUid());
        transaction.setPointsConvertis(points);
        transaction.setMontant(argent);
        transaction.setDate(new Date());
        transaction.setType(transaction.getType());
        transaction.setStatut(transaction.getStatut());
        transaction.setDescription(points + " points convertis en argent (" + argent + " FCFA)");
        transactionRepository.save(transaction);

    }

    public List<TransactionHistorique> recupererHistoriqueParUtilisateur(String firebaseUUID) {
        return transactionRepository.findByUserId(firebaseUUID);
    }

    public Optional<Double> getPointsThisMonth() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        return pointsRepository.sumPointsThisMonth(startOfMonth, today);
    }

    public Double getPointsByUserId(Long userId) {
        return pointsRepository.findPointsByUtilisateurId(userId);
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
