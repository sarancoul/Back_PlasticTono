package com.example.plasti_tono.ConfigMqtt;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Gérer le message WebSocket entrant
        System.out.println("Message reçu : " + message.getPayload());

        // Exemple : Renvoie le message
        session.sendMessage(new TextMessage("Echo : " + message.getPayload()));
    }
}
