package com.inventus.domain.usuario;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.inventus.infra.exception.ValidarCadastroException;

public enum UserRole {
    ADMIN,
    SUPERVISOR,
    FUNCIONARIO;

    @JsonCreator
    public static UserRole fromString(String value) {

        if (value == null || value.trim().isEmpty()) {
            throw new ValidarCadastroException("O campo userRole não pode ser vazio.");
        }

        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new ValidarCadastroException("Por favor, escolha um tipo de usuário válido: ADMIN, SUPERVISOR ou FUNCIONARIO.");
    }
}