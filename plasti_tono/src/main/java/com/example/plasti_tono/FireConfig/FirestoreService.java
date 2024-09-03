package com.example.plasti_tono.FireConfig;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    @Autowired
    Firestore firestore;


    public Map<String, Object> getUserData(String userId) {
        try {
            ApiFuture<DocumentSnapshot> future = firestore.collection("Mobiles").document(userId).get();
            DocumentSnapshot document = future.get();

            if (document.exists()){
                System.out.println("Données de l'utilisateur : " + document.getData());
                return document.getData();

            }else {
                System.out.println("Pas de document trouvé !");
                return null;

            }
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }

   /* public void saveSessionData(Long sessionId, Map<String, Object> sessionData) {
        DocumentReference docRef = firestore.collection("Sessions").document(String.valueOf(sessionId));
        ApiFuture<WriteResult> result = docRef.set(sessionData);
        try {
            result.get();
            System.out.println("Session sauvegardée avec succès dans Firestore");
        } catch (Exception e) {
            System.err.println("Erreur lors de la sauvegarde de la session: " + e.getMessage());
        }
    }*/
   public void saveSessionData(Long sessionId, Map<String, Object> sessionData) {
       DocumentReference docRef = firestore.collection("Sessions").document(String.valueOf(sessionId));
       ApiFuture<WriteResult> result = docRef.set(sessionData);
       try {
           WriteResult writeResult = result.get();
           System.out.println("Session sauvegardée avec succès dans Firestore à " + writeResult.getUpdateTime());
       } catch (Exception e) {
           System.err.println("Erreur lors de la sauvegarde de la session: " + e.getMessage());
       }
   }
}

