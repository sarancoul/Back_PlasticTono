package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PointsRepository extends JpaRepository<Points, Long>{
   // Points findBySession(long session);

    Points findFirstBySessionIdSession(long id);

    Points findFirstBySession(Session session);

    @Query("SELECT SUM(p.points) FROM Points p WHERE p.utilisateur.idUtilisateur= :userId")
    Double findPointsByUtilisateurId(@Param("userId") Long userId);

    @Query("SELECT SUM(p.points) FROM Points p WHERE p.session.datedebut BETWEEN :startOfMonth AND :today")
    Optional<Double> sumPointsThisMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("today") LocalDateTime today);


}
