package com.inventus.services.impl;

import com.inventus.domain.dto.CadastrarUsuarioDto;
import com.inventus.domain.dto.UsuarioDto;
import com.inventus.domain.status.Status;
import com.inventus.domain.usuario.AutenticacaoUsuario;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.CredenciaisInvalidasException;
import com.inventus.infra.security.DadosTokenJWT;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.UsuarioRepository;
import com.inventus.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public UsuarioDto cadastrarUsuario(CadastrarUsuarioDto usuarioDto) {

        String senha = passwordEncoder.encode(usuarioDto.senha());

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDto.nome());
        usuario.setEmail(usuarioDto.email());
        usuario.setSenha(senha);
        usuario.setTelefone(usuarioDto.telefone().replace(" ", ""));
        usuario.setUserRole(usuarioDto.userRole());
        usuario.setDataCadastro(LocalDate.now());
        usuario.setStatus(Status.ATIVO);

        Usuario novoUsuario = usuarioRepository.save(usuario);

        return new UsuarioDto(novoUsuario);
    }

    @Override
    public DadosTokenJWT efetuarLogin(AutenticacaoUsuario dados) {
        try {
            var autenticacaoToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            var autenticacao = manager.authenticate(autenticacaoToken);
            var tokenJWT = tokenService.generateToken((Usuario) autenticacao.getPrincipal());

            return new DadosTokenJWT(tokenJWT);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new CredenciaisInvalidasException("Credenciais inválidas fornecidas. Verifique seu email e senha e tente novamente.");
        }
    }
}