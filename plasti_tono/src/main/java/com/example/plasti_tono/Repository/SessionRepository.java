package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUtilisateur(Utilisateurs utilisateur);

    List<Session> findByUtilisateur_IdUtilisateur(Long idUtilisateur);

    Session findFirstByIdSession(Long id);
    @Query("SELECT SUM(s.poids) FROM Session s WHERE DATE(s.datedebut) = CURRENT_DATE")
    Optional<Double> sumWeightsToday();
    @Query("SELECT COUNT(DISTINCT s.utilisateur) FROM Session s WHERE DATE(s.datedebut) = CURRENT_DATE")
    long countActiveUsersToday();

    //Optional<Session> findActiveSessionByUserAndKiosk(Long userId, String kioskCode);

}
