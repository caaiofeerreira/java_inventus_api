package com.inventus.services;

import com.inventus.domain.dto.CadastrarUsuarioDto;
import com.inventus.domain.dto.UsuarioDto;
import com.inventus.domain.usuario.AutenticacaoUsuario;
import com.inventus.infra.security.DadosTokenJWT;

public interface UsuarioService {

    UsuarioDto cadastrarUsuario(CadastrarUsuarioDto usuarioDto);

    DadosTokenJWT efetuarLogin(AutenticacaoUsuario dados);
}
