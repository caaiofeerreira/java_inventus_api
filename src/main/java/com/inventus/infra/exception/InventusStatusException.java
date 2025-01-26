package com.inventus.infra.exception;

public class InventusStatusException extends RuntimeException {
    public InventusStatusException(String mensagem) {
        super(mensagem);
    }
}