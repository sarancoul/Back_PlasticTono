package com.example.plasti_tono.Repository;

import com.example.plasti_tono.Model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
