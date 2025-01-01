package com.inventus.services.impl;

import com.inventus.domain.categoria.Categoria;
import com.inventus.domain.dto.CadastrarCategoriaDto;
import com.inventus.domain.dto.CategoriaDto;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.CategoriaNaoEncontradaException;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.CategoriaRepository;
import com.inventus.services.CategoriaService;
import com.inventus.services.validations.ValidarCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ValidarCategoria validarCategoria;

    @Override
    @Transactional
    public CategoriaDto cadastrarCategoria(String token, CadastrarCategoriaDto cadastrarCategoriaDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }

        validarCategoria.validar(cadastrarCategoriaDto);

        Categoria categoria = Categoria.criarCategoria(
                cadastrarCategoriaDto.nome(),
                cadastrarCategoriaDto.descricao()
        );

        Categoria novaCategoria = categoriaRepository.save(categoria);

        return new CategoriaDto(novaCategoria);
    }

    @Override
    public List<CategoriaDto> listarCategorias(String token) {

        tokenService.getUserFromToken(token);

        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty()) {
            throw new CategoriaNaoEncontradaException("Lista de Categorias vazia.");
        }

        return categorias.stream()
                .map(CategoriaDto::new)
                .toList();
    }

    @Override
    public CategoriaDto buscarCategoria(String token, Long id) {

        tokenService.getUserFromToken(token);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada."));

        return new CategoriaDto(categoria);
    }
}
