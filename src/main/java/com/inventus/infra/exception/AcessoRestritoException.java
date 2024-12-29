package com.inventus.infra.exception;

public class AcessoRestritoException extends RuntimeException {
    public AcessoRestritoException(String mensagem) {
        super(mensagem);
    }
}