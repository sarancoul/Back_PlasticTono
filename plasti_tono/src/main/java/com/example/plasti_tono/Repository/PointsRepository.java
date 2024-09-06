package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Points;
import com.example.plasti_tono.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PointsRepository extends JpaRepository<Points, Long>{
   // Points findBySession(long session);

    Points findFirstBySession(Session session);
}
