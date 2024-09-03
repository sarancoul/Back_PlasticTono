package com.example.plasti_tono.Controller;
import com.example.plasti_tono.FireConfig.FirestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirebaseController {

    FirestoreService firestoreService;

    public FirebaseController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }


    @GetMapping("/firebase")
    public ResponseEntity<Object> getUserData(@RequestParam(name = "userId") String userId) {
        return ResponseEntity.accepted().body(firestoreService.getUserData(userId));
    }
}
