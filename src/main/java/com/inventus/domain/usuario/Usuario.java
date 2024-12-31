package com.inventus.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventus.domain.status.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name="Usuario")
@Table(name="tb_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha;

    @Column(unique = true)
    private String telefone;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static Usuario criarUsuario(String nome, String email, String senha, String telefone,
                                       UserRole userRole, LocalDate dataCadastro, Status status) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTelefone(telefone);
        usuario.setUserRole(userRole);
        usuario.setDataCadastro(dataCadastro);
        usuario.setStatus(status);

        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}