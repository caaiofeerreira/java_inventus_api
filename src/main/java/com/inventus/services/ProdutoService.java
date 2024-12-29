package com.inventus.services;

import com.inventus.domain.dto.CadastrarProdutoDto;
import com.inventus.domain.dto.ProdutoDto;

public interface ProdutoService {

    ProdutoDto cadastrarProduto(String token, CadastrarProdutoDto cadastrarProdutoDto);
}
