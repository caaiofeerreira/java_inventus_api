package com.inventus.domain.dto;

import com.inventus.domain.categoria.Categoria;

public record CategoriaDto(Long id,
                           String nome,
                           String descricao) {

    public CategoriaDto(Categoria categoria) {
        this(categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }
}