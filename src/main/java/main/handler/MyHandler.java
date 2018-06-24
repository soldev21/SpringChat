package main.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static main.utility.Log.*;

@Component
public class MyHandler extends TextWebSocketHandler {



    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession){
        info("New connection established Session id : "+webSocketSession.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage message){
        info("New message from session id: "+webSocketSession.getId()+" message: "+message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status){
        info("Session closed id: "+webSocketSession.getId());
    }
}
