package com.inventus.domain.movimento;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoMovimento {
    ENTRADA,
    SAIDA;

    @JsonCreator
    public static TipoMovimento fromString(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Tipo Movimento não pode ser nulo ou vazio.");
        }

        for (TipoMovimento tipoMovimento : TipoMovimento.values()) {
            if (tipoMovimento.name().equalsIgnoreCase(value)) {
                return tipoMovimento;
            }
        }
        throw new IllegalArgumentException("Por favor, escolha um tipo de movimento válido: ENTRADA ou SAIDA.");
    }
}