package com.inventus.controller;

import com.inventus.domain.dto.usuario.CadastrarUsuarioDto;
import com.inventus.domain.usuario.AutenticacaoUsuario;
import com.inventus.infra.security.DadosTokenJWT;
import com.inventus.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventus")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarUsuarioDto usuarioDto) {

        usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sua conta foi cadastrada com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid AutenticacaoUsuario dados) {

        DadosTokenJWT dadosTokenJWT = usuarioService.efetuarLogin(dados);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(dadosTokenJWT);
    }
}