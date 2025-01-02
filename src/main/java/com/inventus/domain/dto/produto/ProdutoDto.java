package com.inventus.domain.dto.produto;

import com.inventus.domain.produto.Produto;
import com.inventus.domain.status.Status;

import java.math.BigDecimal;

public record ProdutoDto(Long id,
                         String nome,
                         String codigoProduto,
                         Integer quantidade,
                         String unidadeMedida,
                         BigDecimal precoCompra,
                         String descricao,
                         Status status) {

    public ProdutoDto(Produto produto) {
        this(produto.getId(),
                produto.getNome(),
                produto.getCodigoProduto(),
                produto.getQuantidade(),
                produto.getUnidadeMedida(),
                produto.getPrecoCompra(),
                produto.getDescricao(),
                produto.getStatus());
    }
}