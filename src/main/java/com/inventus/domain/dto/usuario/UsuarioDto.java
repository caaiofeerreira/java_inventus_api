package com.inventus.domain.dto.usuario;

import com.inventus.domain.usuario.Usuario;

public record UsuarioDto(Long id,
                         String nome,
                         String senha,
                         String email,
                         String telefone) {

    public UsuarioDto(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNome(),
                usuario.getSenha(),
                usuario.getEmail(),
                usuario.getTelefone());
    }
}
