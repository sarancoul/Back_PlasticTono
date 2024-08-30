package com.example.plasti_tono.Controller;

import com.example.plasti_tono.ConfigMqtt.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttService mqttService;

    @PostMapping("/publier")
    public ResponseEntity<String> publierMessage(@RequestParam String topic, @RequestParam String message){
        try {
            mqttService.publish(topic, message);
            return new ResponseEntity<>("Message publié avec succès", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erreur lors de la publication : " +e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> sabonnerSujet(@RequestParam String topic){
        try {
            mqttService.subscribe(topic);
            return new ResponseEntity<>("Abonnement au sujet reussi", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Erreur lors de l'abonnement " +e.getMessage(), HttpStatus.OK);
        }
    }

    /////////////////////////lien node red http://127.0.0.1:1880/#flow/b664cc09832da82c//////////////:::::::::::::::::

    //////////////////////::::::::lien manage pallette video https://youtu.be/plpTjsbtYpo?si=DCBBHM_pXv74ckhD:::::::::::::::::
    ///////////////////:::::::::::::::::::::: doc instal node red https://nodered.org/docs/getting-started/windows#running-on-windows:::::////
}
