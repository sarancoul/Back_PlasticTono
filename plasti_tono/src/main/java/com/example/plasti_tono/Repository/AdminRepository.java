package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByNomAndMotDePasse(String nom, String motDePasse);
}
