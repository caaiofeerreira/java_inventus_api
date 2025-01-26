package com.inventus.infra.exception;

public class MovimentoInvalidoException extends RuntimeException {
    public MovimentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}