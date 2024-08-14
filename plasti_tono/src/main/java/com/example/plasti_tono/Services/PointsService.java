package com.example.plasti_tono.Services;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Repository.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointsService {
    private  final PointsRepository pointsRepository;

    @Autowired
    public PointsService(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    public Points EnregistrePoint(Session session, double poids){

        int points = calculerPoints(poids);
        Points nouveauPoints = new Points();
        nouveauPoints.setPoids(poids);
        nouveauPoints.setPoints(points);
        nouveauPoints.setSession(session);

        return pointsRepository.save(nouveauPoints);
    }

    private int calculerPoints(double poids){
        if (poids >= 10){
            return 25;
        } else if (poids >= 5) {
            return 10;
        } else if (poids >=1) {
            return 2;

        }else {
            return 0;
        }
    }
}
