package com.example.plasti_tono.Services;

import com.example.plasti_tono.Model.PointTotal;
import com.example.plasti_tono.Model.Utilisateurs;
import com.example.plasti_tono.Repository.PointTotalRepository;
import com.example.plasti_tono.Repository.UtilisateursRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PointTotalService {
    PointTotalRepository pointTotalRepository;
    UtilisateursRepository utilisateursRepository;

    public PointTotal saveOrUpdatePoint(Utilisateurs utilisateurs, double point){
        PointTotal total=pointTotalRepository.findFirstByUtilisateurIdUtilisateur(utilisateurs.getIdUtilisateur());

        System.out.println("---------------------------------------");
        System.out.println(total);
        if (total==null){
            total=new PointTotal(null,0.0,utilisateurs);
        }
        total.setPoint(total.getPoint()+point);
        System.out.println("---------------------------------------point2");
        System.out.println(total);
        return pointTotalRepository.save(total);
    }

    public PointTotal getPointTotal(Utilisateurs utilisateurs){
        return pointTotalRepository.findFirstByUtilisateur(utilisateurs);
    }
}
