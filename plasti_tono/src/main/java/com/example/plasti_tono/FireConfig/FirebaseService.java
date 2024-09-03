package com.example.plasti_tono.FireConfig;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    public FirebaseToken verifyToken(String token) throws Exception{
        return FirebaseAuth.getInstance().verifyIdToken(token);
    }
}
