package com.mggcode.cliente_elecciones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;

@ControllerAdvice
public class ExceptionAdvicer extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ConnectException.class})
    protected ResponseEntity<Object> handleConnection() {
        System.err.println("Conexión con el servidor no encontrada");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Conexión perdida");
    }

}
