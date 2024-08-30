package com.example.plasti_tono.Services;


import com.example.plasti_tono.Model.Admin;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.AdminRepository;
import com.example.plasti_tono.Repository.UtilisateursRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UtilisateursRepository utilisateursRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, UtilisateursRepository utilisateursRepository) {
        this.adminRepository = adminRepository;
        this.utilisateursRepository = utilisateursRepository;
    }

    /////////////////////////////login admin////////////////////////////////////
    public Admin loginAdmin(String nom, String motDePasse) {
        return adminRepository.findByNomAndMotDePasse(nom, motDePasse)
                .orElseThrow(() -> new EntityNotFoundException("Cet utilisateur n'existe pas  "));
    }
////////////////////desactiver un user/////////////////////////////////
public void activerUtilisateur(Long idUtilisateur) {
    Utilisateurs utilisateur = utilisateursRepository.findById(idUtilisateur)
            .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'Id : " + idUtilisateur));
    utilisateur.setActive(true);
    utilisateursRepository.save(utilisateur);
}

    public void desactiverUtilisateur(Long idUtilisateur) {
        Utilisateurs utilisateur = utilisateursRepository.findById(idUtilisateur)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'Id : " + idUtilisateur));
        utilisateur.setActive(false);
        utilisateursRepository.save(utilisateur);
    }

    ////////////////////afficher tous les admin///////////////////
    public List<Admin> getAllAdmins(){return adminRepository.findAll();}


}
