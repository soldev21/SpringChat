package main.configuration;

import main.handler.clienthandler.MyHandler;
import main.handler.clienthandler.MyHandlerForPerConnection;
import main.registry.ClientRegistry;
import main.registry.HashMapClientRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebScoketConfig implements WebSocketConfigurer {

    @Autowired
    MyHandler myHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler,"/chat").setAllowedOrigins("*")
        .withSockJS();
    }



    @Bean
    public WebSocketHandler getHandlerPerConnection(){
        return new PerConnectionWebSocketHandler(MyHandlerForPerConnection.class);
    }

    @Bean
    public ClientRegistry clientRegistry(){
        return new HashMapClientRegistry<WebSocketSession>();
    }
}
