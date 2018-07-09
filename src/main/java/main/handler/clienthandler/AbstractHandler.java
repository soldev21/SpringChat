package main.handler.clienthandler;

import main.handler.messagehandler.AbstractMessageHandler;
import main.registry.ClientRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by Sherif on 7/7/2018.
 */
public class AbstractHandler extends TextWebSocketHandler {

    protected ClientRegistry registry;
    protected AbstractMessageHandler messageHandler;

    public AbstractHandler(ClientRegistry registry,AbstractMessageHandler messageHandler){
        this.registry = registry;
        this.messageHandler = messageHandler;
    }
}
