package com.inventus.services.validations;

import com.inventus.domain.dto.CadastrarCategoriaDto;
import com.inventus.infra.exception.ValidarCadastroException;
import com.inventus.repositories.CategoriaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCategoria {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final String NOME_REGEX = "[A-Za-z0-9 ]+";

    public void validar(CadastrarCategoriaDto cadastrarCategoriaDto) {

        validarNome(cadastrarCategoriaDto);
        validarDescricao(cadastrarCategoriaDto);
    }

    private void validarNome(CadastrarCategoriaDto cadastrarCategoriaDto) {

        String nome = cadastrarCategoriaDto.nome().trim().toLowerCase();

        if (nome.isEmpty() || !nome.matches(NOME_REGEX)) {
            throw new ValidarCadastroException("O nome da categoria é obrigatório e deve conter apenas letras, números e espaços.");
        }

        categoriaRepository.findByNomeCategoria(nome)
                .ifPresent(categoria -> {
                    throw new ValidarCadastroException("O nome da categoria sugerido já possui cadastro.");
                });

        categoriaRepository.findAll()
                .forEach(categoria -> {

                    int distancia = StringUtils.getLevenshteinDistance(nome, categoria.getNome().toLowerCase());

                    if (distancia <= 3) {
                        throw new ValidarCadastroException("Já existe uma categoria com nome similar a '" + categoria.getNome() + "'.");
                    }
                });
    }

    private void validarDescricao(CadastrarCategoriaDto cadastrarCategoriaDto) {

        String descricao = cadastrarCategoriaDto.descricao().trim();

        if (descricao.isEmpty()) {
            throw new ValidarCadastroException("A descriçao da categoria é obrigatório.");
        }
    }
}