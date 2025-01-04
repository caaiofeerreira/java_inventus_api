package com.inventus.infra.exception;

public class MovimentoEstoqueNaoEncontradoExeption extends RuntimeException {
    public MovimentoEstoqueNaoEncontradoExeption(String mensagem) {
        super(mensagem);
    }
}