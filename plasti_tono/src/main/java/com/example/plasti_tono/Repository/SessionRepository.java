package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUtilisateur(Utilisateurs utilisateur);

    List<Session> findByUtilisateur_IdUtilisateur(Long idUtilisateur);

    //Optional<Session> findActiveSessionByUserAndKiosk(Long userId, String kioskCode);

}
