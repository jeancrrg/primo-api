package com.primo.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primo.dto.InformacaoClienteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PrestadorWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long codigoPrestador = Long.valueOf(session.getUri().getPath().split("/ws/prestador/")[1]);
        sessions.put(codigoPrestador, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        // Aqui você pode tratar respostas dos prestadores (ex: aceitar/recusar serviço)
        System.out.println("Mensagem recebida do prestador: " + payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.values().removeIf(s -> s.getId().equals(session.getId()));
    }

    public void enviarSolicitacao(Long codigoPrestador, InformacaoClienteDTO informacaoClienteDTO) throws IOException {
        WebSocketSession session = sessions.get(codigoPrestador);
        if (session != null && session.isOpen()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(informacaoClienteDTO);
            session.sendMessage(new TextMessage(payload));
        }
    }

}

