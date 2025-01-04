package com.inventus.services.validations;

import com.inventus.domain.dto.categoria.CadastrarCategoriaDto;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.ValidarCadastroException;
import com.inventus.repositories.CategoriaRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCadastroCategoria {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final String NOME_REGEX = "[A-Za-z0-9 ]+";

    public void validar(Usuario usuario, CadastrarCategoriaDto cadastrarCategoriaDto) {

        validarUsuario(usuario);
        validarNome(cadastrarCategoriaDto);
        validarDescricao(cadastrarCategoriaDto);
    }

    private void validarUsuario(Usuario usuario) {

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }
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

                    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
                    int distancia = levenshteinDistance.apply(nome.toLowerCase(), categoria.getNome().toLowerCase());

                    if (distancia <= 3) {
                        throw new ValidarCadastroException("Já existe uma categoria com nome similar a '" + categoria.getNome() + "'.");
                    }
                });
    }

    private void validarDescricao(CadastrarCategoriaDto cadastrarCategoriaDto) {

        String descricao = cadastrarCategoriaDto.descricao().trim();

        if (descricao.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça a descriçao da categoria. Este campo é obrigatório.");
        }
    }
}