package com.mggcode.cliente_elecciones.conexion;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class WebSocketSessionHandler extends StompSessionHandlerAdapter{
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.setAutoReceipt(true);

        System.out.println("Conexión establecida");
        session.subscribe("/autonomicas/updates", this);

    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.err.println("ERROR: " + exception.getMessage());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String message = (String) payload;
        System.out.println("Recibido: " + message);
    }
}
