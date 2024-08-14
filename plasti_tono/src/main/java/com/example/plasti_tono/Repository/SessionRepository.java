package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUtilisateur(Utilisateurs utilisateur);
}
