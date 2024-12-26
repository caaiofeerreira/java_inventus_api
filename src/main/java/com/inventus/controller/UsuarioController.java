package com.inventus.controller;

import com.inventus.domain.dto.CadastrarUsuarioDto;
import com.inventus.domain.dto.UsuarioDto;
import com.inventus.domain.usuario.AutenticacaoUsuario;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.CredenciaisInvalidasException;
import com.inventus.infra.security.DadosTokenJWT;
import com.inventus.infra.security.TokenService;
import com.inventus.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventus")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarUsuarioDto usuarioDto) {
        usuarioService.cadastrarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sua conta foi cadastrada com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid AutenticacaoUsuario dados) {
        try {
            var autenticacaoToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            var autenticacao = manager.authenticate(autenticacaoToken);
            var tokenJWT = tokenService.generateToken((Usuario) autenticacao.getPrincipal());

            return ResponseEntity.status(HttpServletResponse.SC_OK).body(new DadosTokenJWT(tokenJWT));
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new CredenciaisInvalidasException("Credenciais inválidas fornecidas. Verifique seu email e senha e tente novamente.");
        }
    }
}