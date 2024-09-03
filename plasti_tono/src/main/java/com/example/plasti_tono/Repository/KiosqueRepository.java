package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Kiosque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KiosqueRepository extends JpaRepository<Kiosque, Long> {
    Optional<Kiosque> findByCode(String code);
}
