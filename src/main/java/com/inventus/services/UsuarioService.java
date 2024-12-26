package com.inventus.services;

import com.inventus.domain.dto.CadastrarUsuarioDto;
import com.inventus.domain.dto.UsuarioDto;

public interface UsuarioService {

    UsuarioDto cadastrarUsuario(CadastrarUsuarioDto usuarioDto);
}
