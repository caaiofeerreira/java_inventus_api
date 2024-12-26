package com.inventus.infra.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}