package com.inventus.infra.exception;

public class CategoriaNaoEncontrada extends RuntimeException {
    public CategoriaNaoEncontrada(String mensagem) {
        super(mensagem);
    }
}