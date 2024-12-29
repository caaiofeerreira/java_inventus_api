package com.inventus.infra.exception;

public class FornecedorNaoEncontradoException extends RuntimeException {
    public FornecedorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}