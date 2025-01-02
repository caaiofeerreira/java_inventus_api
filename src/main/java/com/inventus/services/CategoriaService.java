package com.inventus.services;

import com.inventus.domain.dto.categoria.CadastrarCategoriaDto;
import com.inventus.domain.dto.categoria.CategoriaDto;

import java.util.List;

public interface CategoriaService {

    CategoriaDto cadastrarCategoria(String token, CadastrarCategoriaDto cadastrarCategoriaDto);

    List<CategoriaDto> listarCategorias(String token);

    CategoriaDto buscarCategoria(String token, Long id);
}
