package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.PointTotal;
import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import com.example.plasti_tono.Model.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PointTotalRepository extends JpaRepository<PointTotal, Long> {
    PointTotal findFirstByUtilisateur(Utilisateurs utilisateurs);
    PointTotal findFirstByUtilisateurIdUtilisateur(long id);


}
