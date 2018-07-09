package main.handler.clienthandler;

import main.handler.messagehandler.AbstractMessageHandler;
import main.model.request.Body;
import main.model.request.RequestObject;
import main.model.response.ResponseHeader;
import main.model.response.ResponseObject;
import main.registry.ClientRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

import static main.utility.Log.info;

@Component
public class MyHandler extends AbstractHandler {


    @Autowired
    public MyHandler(ClientRegistry registry, AbstractMessageHandler messageHandler) {
        super(registry, messageHandler);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession){
        info("New connection.Client id : " + webSocketSession.getId());
        publishAll(1, webSocketSession.getId());
        try {
            sendMessage(webSocketSession,buildResponseObject(3,null,messageHandler.stringify(
                    new ArrayList<String>(registry.getAll().keySet()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        registry.addClient(webSocketSession.getId(), webSocketSession);
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        //info("New message from client id: "+webSocketSession.getId()+" message: "+message.getPayload());
        try {
            handleMessage(webSocketSession, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        info("Client gone id: " + webSocketSession.getId());
        registry.remove(webSocketSession.getId());
        publishAll(2, webSocketSession.getId());
    }

    private void handleMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {
        info(message.getPayload());
        RequestObject msg = messageHandler.convertToObject(message.getPayload(), RequestObject.class);
        ResponseObject responseMessage = buildResponseObject(0, webSocketSession.getId(), msg.getBody().getMessage());
        sendMessage(msg.getHeader().getReceiverId(), responseMessage);

    }

    private void publishAll(int type, String id) {
        ResponseObject responseObject = buildResponseObject(type, null, id);
        registry.getAll().forEach((k, v) -> {
            sendMessage((String) k, responseObject);
        });
    }

    private void sendMessage(String receiverId, ResponseObject message) {
        try {
            sendMessage(((WebSocketSession) registry.getClient(receiverId))
                    ,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(WebSocketSession session, ResponseObject message) {
        try {
            session.sendMessage(new TextMessage(messageHandler.stringify(message)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResponseObject buildResponseObject(Integer type, String senderId, String msg) {
        return new ResponseObject()
                .setHeader(new ResponseHeader()
                        .setType(type)
                        .setSenderId(senderId))
                .setBody(new Body()
                        .setMessage(msg));
    }
}
