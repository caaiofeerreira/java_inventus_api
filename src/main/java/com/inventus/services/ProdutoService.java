package com.inventus.services;

import com.inventus.domain.dto.CadastrarProdutoDto;
import com.inventus.domain.dto.ProdutoDto;

import java.util.List;

public interface ProdutoService {

    ProdutoDto cadastrarProduto(String token, CadastrarProdutoDto cadastrarProdutoDto);

    List<ProdutoDto> listarProdutos(String token);

    ProdutoDto buscarProduto(String token, Long id);

}