package com.primo.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final PrestadorWebSocketHandler prestadorWebSocketHandler;

    public WebSocketConfig(PrestadorWebSocketHandler handler) {
        this.prestadorWebSocketHandler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(prestadorWebSocketHandler, "/ws/prestador/{id}")
                .setAllowedOrigins("*");
    }

}
