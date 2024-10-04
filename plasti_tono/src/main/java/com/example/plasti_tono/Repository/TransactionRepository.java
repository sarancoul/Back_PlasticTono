package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.TransactionHistorique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionHistorique, Long> {
    List<TransactionHistorique> findByUserId(String userId);
}
