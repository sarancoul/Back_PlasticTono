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

    public Map<String, Object> getSessionData(String sessionId) {
        try {
            ApiFuture<DocumentSnapshot> future = firestore.collection("Sessions").document(sessionId).get();
            DocumentSnapshot document = future.get();

            if (document.exists()){
                System.out.println("Données de sessions : " + document.getData());
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

   public void saveSessionData(String sessionId, Map<String, Object> sessionData) {
       DocumentReference docRef = firestore.collection("Sessions").document(sessionId);
       ApiFuture<WriteResult> result = docRef.set(sessionData);
       try {
           WriteResult writeResult = result.get();
           System.out.println("Session sauvegardée avec succès dans Firestore à " + writeResult.getUpdateTime());
       } catch (Exception e) {
           System.err.println("Erreur lors de la sauvegarde de la session: " + e.getMessage());
       }
   }

    public void deleteSessionData(String sessionId) {
        DocumentReference docRef = firestore.collection("Sessions").document(sessionId);
        ApiFuture<WriteResult> result = docRef.delete();  // Utiliser delete() pour supprimer le document
        try {
            WriteResult writeResult = result.get();
            System.out.println("Session supprimée avec succès à " + writeResult.getUpdateTime());
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la session : " + e.getMessage());
        }
    }


    ////////fin session
    public void updateSessionData(String sessionId, Map<String, Object> sessionData) {
        DocumentReference docRef = firestore.collection("Sessions").document(String.valueOf(sessionId));
        ApiFuture<WriteResult> result = docRef.set(sessionData);

        try {
            // Attendre que l'opération se termine et vérifier le résultat
            WriteResult writeResult = result.get();
            System.out.println("Session mise à jour avec succès dans Firestore à " + writeResult.getUpdateTime());
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de la session: " + e.getMessage());
            throw new RuntimeException("Erreur lors de la mise à jour de Firestore", e);
        }
    }






   /* public void deleteSessionData(Long sessionId) {
        // Supprimer le document de la collection "Sessions" dans Firestore
        ApiFuture<WriteResult> result = firestore.collection("Sessions").document(String.valueOf(sessionId)).delete();

        try {
            WriteResult writeResult = result.get();
            System.out.println("Session supprimée avec succès à " + writeResult.getUpdateTime());
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de la session : " + e.getMessage());
        }
    }*/
}

