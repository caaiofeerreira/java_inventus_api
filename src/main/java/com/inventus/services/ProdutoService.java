package com.inventus.services;

import com.inventus.domain.dto.produto.CadastrarProdutoDto;
import com.inventus.domain.dto.produto.ProdutoDto;
import com.inventus.domain.dto.produto.QuantidadeProdutoDto;

import java.util.List;

public interface ProdutoService {

    ProdutoDto cadastrarProduto(String token, CadastrarProdutoDto cadastrarProdutoDto);

    List<ProdutoDto> listarProdutos(String token);

    ProdutoDto buscarProduto(String token, Long id);

    ProdutoDto atualizarQuantidadeProduto(String token, QuantidadeProdutoDto quantidadeProduto);

}