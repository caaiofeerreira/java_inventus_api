package com.inventus.services.validations;

import com.inventus.domain.dto.CadastrarFornecedorDto;
import com.inventus.infra.exception.ValidarCadastroException;
import com.inventus.repositories.FornecedorRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarFornecedor {

    @Autowired
    private FornecedorRepository fornecedorRepository;
    private static final String TELEFONE_REGEX = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$";
    private static final String CNPJ_REGEX = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$";
    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    public void validar(CadastrarFornecedorDto cadastrarFornecedorDto) {

        validarNome(cadastrarFornecedorDto);
        validarCNPJ(cadastrarFornecedorDto);
        validarEndereco(cadastrarFornecedorDto);
        validarTelefone(cadastrarFornecedorDto);
        validarEmail(cadastrarFornecedorDto);

    }

    private void validarNome(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String nome = cadastrarFornecedorDto.nome().trim().toLowerCase();

        if (nome.isEmpty()) {
            throw new ValidarCadastroException("O nome é obrigatorio.");
        }

        fornecedorRepository.findByNomeFornecedor(nome)
                .ifPresent(fornecedor -> {
                    throw new ValidarCadastroException("O nome do fornecedor sugerido já possui cadastro.");
                });
    }

    private void validarCNPJ(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String cnpj = cadastrarFornecedorDto.cnpj().trim();

        if (cnpj.isEmpty()) {
            throw new ValidarCadastroException("O CNPJ é obrigatório.");
        } else if (!cnpj.matches(CNPJ_REGEX)) {
            throw new ValidarCadastroException("O CNPJ informado não possui um formato válido. Por favor, insira o CNPJ no formato XX.XXX.XXX/XXXX-XX.");
        }
    }

    private void validarEndereco(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String endereco = cadastrarFornecedorDto.endereco().trim();

        if (endereco.isEmpty()) {
            throw new ValidarCadastroException("O endereço é obrigatorio.");
        }
    }

    private void validarTelefone(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String telefone = cadastrarFornecedorDto.telefone().trim();

        if (telefone.isEmpty()) {
            throw new ValidarCadastroException("O telefone é obrigatorio.");
        } else if (!telefone.matches(TELEFONE_REGEX)) {
            throw new ValidarCadastroException("O telefone deve estar no formato (XX) XXXXX-XXXX.");
        }
    }

    private void validarEmail(CadastrarFornecedorDto cadastrarFornecedorDto) {

        String email = cadastrarFornecedorDto.email();

        if (StringUtils.isBlank(email)) {
            throw new ValidarCadastroException("O e-mail é obrigatório.");
        }

        if (!emailValidator.isValid(email)) {
            throw new ValidarCadastroException("O e-mail fornecido não é válido.");
        }
    }
}