package com.inventus.infra.exception;

public class ValidarCadastroException extends RuntimeException {
    public ValidarCadastroException(String mensagem) {
        super(mensagem);
    }
}