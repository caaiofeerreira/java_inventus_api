package com.inventus.domain.usuario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.inventus.infra.exception.CargoInvalidoException;

public enum UserRole {
    ADMIN,
    SUPERVISOR,
    FUNCIONARIO;

    @JsonCreator
    public static UserRole fromString(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new CargoInvalidoException("O campo userRole não pode ser nulo ou vazio.");
        }

        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new CargoInvalidoException("Por favor, escolha um tipo de usuário válido: ADMIN, SUPERVISOR ou FUNCIONARIO.");
    }
}