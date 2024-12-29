package com.inventus.services.impl;

import com.inventus.domain.categoria.Categoria;
import com.inventus.domain.dto.CadastrarCategoriaDto;
import com.inventus.domain.dto.CategoriaDto;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.CategoriaNaoEncontrada;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.CategoriaRepository;
import com.inventus.services.CategoriaService;
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

    @Override
    @Transactional
    public CategoriaDto cadastrarCategoria(String token, CadastrarCategoriaDto cadastrarCategoriaDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(cadastrarCategoriaDto.nome());
        categoria.setDescricao(cadastrarCategoriaDto.descricao());

        Categoria novaCategoria = categoriaRepository.save(categoria);

        return new CategoriaDto(novaCategoria);
    }

    @Override
    public List<CategoriaDto> listarCategorias(String token) {

        tokenService.getUserFromToken(token);

        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty()) {
            throw new CategoriaNaoEncontrada("Lista de Categorias vazia.");
        }

        return categorias.stream()
                .map(CategoriaDto::new)
                .toList();
    }

    @Override
    public CategoriaDto buscarCategoria(String token, Long id) {

        tokenService.getUserFromToken(token);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontrada("Categoria não encontrada."));

        return new CategoriaDto(categoria);
    }
}
