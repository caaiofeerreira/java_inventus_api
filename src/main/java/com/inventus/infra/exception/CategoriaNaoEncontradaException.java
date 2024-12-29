package com.inventus.infra.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}