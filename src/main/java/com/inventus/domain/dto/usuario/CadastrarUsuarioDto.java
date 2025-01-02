package com.inventus.domain.dto.usuario;

import com.inventus.domain.usuario.UserRole;

public record CadastrarUsuarioDto(Long id,
                                  String nome,
                                  String email,
                                  String senha,
                                  String telefone,
                                  UserRole userRole) {
}