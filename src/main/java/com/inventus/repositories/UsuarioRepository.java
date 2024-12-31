package com.inventus.repositories;

import com.inventus.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmailExisting(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.telefone = :telefone")
    Optional<Usuario> findByTelefoneExisting(@Param("telefone") String telefone);

}