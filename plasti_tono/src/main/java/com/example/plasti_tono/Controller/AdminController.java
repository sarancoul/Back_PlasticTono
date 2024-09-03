package com.example.plasti_tono.Controller;

import com.example.plasti_tono.ApiResponse;
import com.example.plasti_tono.Model.Admin;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.AdminRepository;
import com.example.plasti_tono.Services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    /////////////////////////login admin////////////////////////////////////
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestParam String nom, @RequestParam String motDePasse) {
        Admin admin = adminService.loginAdmin(nom, motDePasse);
        return new ResponseEntity<>("Connecté avec succès", HttpStatus.OK);
    }

    ///////////////////////////Activer un user///////////////////////////////
    @PutMapping("/activer/{idUtilisateur}")
    public ResponseEntity<String> activerUtilisateur(@PathVariable Long idUtilisateur) {
        adminService.activerUtilisateur(idUtilisateur);
        return new ResponseEntity<>("Utilisateur activé avec succès", HttpStatus.OK);
    }

    @PutMapping("/desactiver/{idUtilisateur}")
    public ResponseEntity<String> desactiverUtilisateur(@PathVariable Long idUtilisateur) {
        adminService.desactiverUtilisateur(idUtilisateur);
        return new ResponseEntity<>("Utilisateur désactivé avec succès", HttpStatus.OK);
    }

    ///////////////GetAllAdmin////////////////////////
    @GetMapping("/read")
    public ResponseEntity<List<Admin>> getAllAdmin() {
        List<Admin> admins = adminService.getAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

}
