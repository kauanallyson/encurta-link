package com.kauanallyson.encurtalink.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredDateException.class)
    public ResponseEntity<StandardError> handleExpiredDateException(ExpiredDateException ex, HttpServletRequest request) {
        log.warn("Tentativa de acesso a link expirado. IP: [{}] URI: [{}] Msg: {}",
                request.getRemoteAddr(), request.getRequestURI(), ex.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardError err = new StandardError(Instant.now(), status.value(), "Data Expirada", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Recurso não encontrado. URI: [{}] Msg: {}", request.getRequestURI(), ex.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError err = new StandardError(Instant.now(), status.value(), "Recurso não encontrado",
                ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado no sistema ao acessar [{}].", request.getRequestURI(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError err = new StandardError(Instant.now(), status.value(),
                "Erro Interno do Servidor", "Ocorreu um erro inesperado. Contate o suporte.", request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}