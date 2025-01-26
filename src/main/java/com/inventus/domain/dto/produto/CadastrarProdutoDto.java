package com.inventus.domain.dto.produto;

import com.inventus.domain.movimento.TipoMovimento;

import java.math.BigDecimal;

public record CadastrarProdutoDto(String nome,
                                  String codigoProduto,
                                  int quantidade,
                                  TipoMovimento tipoMovimento,
                                  String unidadeMedida,
                                  BigDecimal precoCompra,
                                  String descricao,
                                  Long categoriaId,
                                  Long fornecedorId,
                                  String motivo) {
}