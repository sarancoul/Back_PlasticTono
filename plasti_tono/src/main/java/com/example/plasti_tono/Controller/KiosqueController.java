package com.example.plasti_tono.Controller;

import com.example.plasti_tono.Model.Kiosque;
import com.example.plasti_tono.Repository.KiosqueRepository;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/kiosque")
public class KiosqueController {

    final KiosqueRepository kiosqueRepository;

    @PostMapping("/Create")
    public ResponseEntity<Kiosque> createKiosque(@RequestBody Kiosque kiosque) {
        Kiosque newKiosque = kiosqueRepository.save(kiosque);
        return new ResponseEntity<>(newKiosque, HttpStatus.CREATED);
    }

    @GetMapping("/allkiosque")
    public ResponseEntity<List<Kiosque>> getAllKiosque() {
        List<Kiosque> kiosques = kiosqueRepository.findAll();
        return new ResponseEntity<>(kiosques, HttpStatus.OK);
    }

}
