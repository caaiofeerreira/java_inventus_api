package com.inventus.services;

import com.inventus.domain.dto.CadastrarCategoriaDto;
import com.inventus.domain.dto.CategoriaDto;

import java.util.List;

public interface CategoriaService {

    CategoriaDto cadastrarCategoria(String token, CadastrarCategoriaDto cadastrarCategoriaDto);

    List<CategoriaDto> listarCategorias(String token);

    CategoriaDto buscarCategoria(String token, Long id);
}
