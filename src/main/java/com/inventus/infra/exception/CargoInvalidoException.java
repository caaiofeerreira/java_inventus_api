package com.inventus.infra.exception;

public class CargoInvalidoException extends RuntimeException {
    public CargoInvalidoException(String mensagem) {
        super(mensagem);
    }
}