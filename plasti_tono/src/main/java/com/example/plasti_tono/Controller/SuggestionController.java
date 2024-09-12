package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Model.Suggestion;
import com.example.plasti_tono.Repository.SuggestionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/Suggestions")
public class SuggestionController {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @PostMapping("/create")
    public ResponseEntity<Suggestion> addSuggestion(@RequestBody Suggestion suggestion) {
        Suggestion savedSuggestion = suggestionRepository.save(suggestion);
        return new ResponseEntity<>(savedSuggestion, HttpStatus.CREATED);
    }

}
