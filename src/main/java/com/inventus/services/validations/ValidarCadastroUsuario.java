package com.inventus.services.validations;

import com.inventus.domain.dto.CadastrarUsuarioDto;
import com.inventus.infra.exception.ValidarCadastroException;
import com.inventus.repositories.UsuarioRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCadastroUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String NOME_REGEX = "[A-Za-zÀ-ÿ\\s'-]+";
    private static final String SENHA_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String TELEFONE_REGEX = "\\(\\d{2}\\) \\d{5}-\\d{4}";

    public void validar(CadastrarUsuarioDto cadastrarUsuarioDto) {

        validarNome(cadastrarUsuarioDto);
        validarEmail(cadastrarUsuarioDto);
        validarSenha(cadastrarUsuarioDto);
        validarTelefone(cadastrarUsuarioDto);
    }

    private void validarNome(CadastrarUsuarioDto cadastrarUsuarioDto) {

        String nome  = cadastrarUsuarioDto.nome().trim();

        if (nome.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o nome do usuario. Este campo é obrigatório.");
        }

        if (nome.length() < 3 || !cadastrarUsuarioDto.nome().matches(NOME_REGEX)) {
            throw new ValidarCadastroException("O nome deve conter pelo menos 3 caracteres e deve conter apenas letras, espaços, hífens e apóstrofos.");
        }
    }

    private void validarEmail(CadastrarUsuarioDto cadastrarUsuarioDto) {

        usuarioRepository.findByEmailExisting(cadastrarUsuarioDto.email()).ifPresent(email -> {
            throw new ValidarCadastroException("O e-mail fornecido já está associado a uma conta existente.");
        });

        if (cadastrarUsuarioDto.email() == null || cadastrarUsuarioDto.email().isEmpty()) {
            throw new ValidarCadastroException("OPor favor, forneça o e-mail do usuario. Este campo não pode ser nulo ou vazio.");
        } else {
            EmailValidator validator = EmailValidator.getInstance();

            if (!validator.isValid(cadastrarUsuarioDto.email())) {
                throw new ValidarCadastroException("O e-mail fornecido não é válido.");
            }
        }
    }

    private void validarSenha(CadastrarUsuarioDto cadastrarUsuarioDto) {

        if (!cadastrarUsuarioDto.senha().matches(SENHA_REGEX)) {
            throw new ValidarCadastroException("A senha deve ter no mínimo 8 caracteres, uma letra maiúscula, uma letra minúscula, um número e um caractere especial.");
        }
    }

    private void validarTelefone(CadastrarUsuarioDto cadastrarUsuarioDto) {

        usuarioRepository.findByTelefoneExisting(cadastrarUsuarioDto.telefone().replace(" ", "")).ifPresent(telefone -> {
            throw new ValidarCadastroException("O telefone fornecido já está associado a uma conta existente.");
        });

        if (cadastrarUsuarioDto.telefone().trim().isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o telefone do usuario. Este campo é obrigatório.");

        } else if (!cadastrarUsuarioDto.telefone().matches(TELEFONE_REGEX)) {
            throw new ValidarCadastroException("O telefone deve estar no formato (XX) XXXXX-XXXX");
        }
    }
}