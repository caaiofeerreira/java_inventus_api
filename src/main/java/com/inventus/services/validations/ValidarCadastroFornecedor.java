package com.inventus.services.validations;

import com.inventus.domain.dto.CadastrarFornecedorDto;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.ValidarCadastroException;
import com.inventus.repositories.FornecedorRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCadastroFornecedor {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private static final String NOME_REGEX = "[A-Za-z0-9 ]+";
    private static final String TELEFONE_REGEX = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$";
    private static final String CNPJ_REGEX = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$";
    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    public void validar(Usuario usuario, CadastrarFornecedorDto cadastrarFornecedorDto) {

        validarUsuario(usuario);
        validarNome(cadastrarFornecedorDto);
        validarCNPJ(cadastrarFornecedorDto);
        validarEndereco(cadastrarFornecedorDto);
        validarTelefone(cadastrarFornecedorDto);
        validarEmail(cadastrarFornecedorDto);

    }

    private void validarUsuario(Usuario usuario) {

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }
    }

    private void validarNome(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String nome = cadastrarFornecedorDto.nome().trim().toLowerCase();

        if (nome.isEmpty() || !nome.matches(NOME_REGEX)) {
            throw new ValidarCadastroException("O nome do fornecedor é obrigatório e deve conter apenas letras, números e espaços.");
        }

        fornecedorRepository.findByNomeFornecedor(nome)
                .ifPresent(fornecedor -> {
                    throw new ValidarCadastroException("O nome do fornecedor sugerido já possui cadastro.");
                });
    }

    private void validarCNPJ(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String cnpj = cadastrarFornecedorDto.cnpj().trim();

        if (cnpj.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o CNPJ do fornecedor. Este campo é obrigatório.");
        } else if (!cnpj.matches(CNPJ_REGEX)) {
            throw new ValidarCadastroException("O CNPJ informado não possui um formato válido. Por favor, insira o CNPJ no formato XX.XXX.XXX/XXXX-XX.");
        }

        fornecedorRepository.findByCnpjFornecedor(cadastrarFornecedorDto.cnpj())
                .ifPresent(fornecedor -> {
                    throw new ValidarCadastroException("O CNPJ do fornecedor sugerido já possui cadastro.");
                });
    }

    private void validarEndereco(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String endereco = cadastrarFornecedorDto.endereco().trim();

        if (endereco.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o endereço do fornecedor. Este campo é obrigatório.");
        }
    }

    private void validarTelefone(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String telefone = cadastrarFornecedorDto.telefone().trim();

        if (telefone.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o telefone do fornecedor. Este campo é obrigatório.");
        } else if (!telefone.matches(TELEFONE_REGEX)) {
            throw new ValidarCadastroException("O telefone deve estar no formato (XX) XXXXX-XXXX.");
        }
    }

    private void validarEmail(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String email = cadastrarFornecedorDto.email();

        if (StringUtils.isBlank(email)) {
            throw new ValidarCadastroException("Por favor, forneça o e-mail do fornecedor. Este campo é obrigatório.");
        }

        if (!emailValidator.isValid(email)) {
            throw new ValidarCadastroException("O e-mail fornecido não é válido.");
        }
    }
}