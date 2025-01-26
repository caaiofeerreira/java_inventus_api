package com.inventus.domain.status;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ATIVO,
    INATIVO;

    @JsonCreator
    public static Status fromString(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo status não pode ser nulo ou vazio.");
        }

        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Por favor, escolha um tipo de status válido: ATIVO ou INATIVO.");
    }
}