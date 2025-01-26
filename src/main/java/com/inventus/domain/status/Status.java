package com.inventus.domain.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.inventus.infra.exception.InventusStatusException;

public enum Status {
    ATIVO,
    INATIVO;

    @JsonCreator
    public static Status fromString(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new InventusStatusException("O campo status não pode ser nulo ou vazio.");
        }

        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new InventusStatusException("Por favor, escolha um tipo de status válido: ATIVO ou INATIVO.");
    }
}