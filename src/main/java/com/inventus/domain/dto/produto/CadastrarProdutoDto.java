package com.inventus.domain.dto.produto;

import java.math.BigDecimal;

public record CadastrarProdutoDto(String nome,
                                  String codigoProduto,
                                  Integer quantidade,
                                  String unidadeMedida,
                                  BigDecimal precoCompra,
                                  String descricao,
                                  Long categoriaId,
                                  Long fornecedorId,
                                  String motivo) {
}