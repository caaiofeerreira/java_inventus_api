package com.inventus.infra.exception;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }
}