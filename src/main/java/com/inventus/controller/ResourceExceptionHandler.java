package com.inventus.controller;

import com.inventus.infra.exception.AutenticacaoException;
import com.inventus.infra.exception.CredenciaisInvalidasException;
import com.inventus.infra.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<ErrorResponse> handlerAutenticacao(AutenticacaoException autenticacaoException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                autenticacaoException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErrorResponse> handlerCredenciaisInvalidas(CredenciaisInvalidasException credenciaisInvalidasException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                credenciaisInvalidasException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}