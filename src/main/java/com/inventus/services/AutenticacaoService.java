package com.inventus.services;

import com.inventus.infra.exception.AutenticacaoException;
import com.inventus.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws AutenticacaoException {

        UserDetails usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new AutenticacaoException("Usuario nao encontrado.");
        }

        return usuario;
    }
}