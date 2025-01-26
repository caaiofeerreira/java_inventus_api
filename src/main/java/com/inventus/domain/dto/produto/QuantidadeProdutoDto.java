package com.inventus.domain.dto.produto;

import com.inventus.domain.movimento.TipoMovimento;

public record QuantidadeProdutoDto(Long id,
                                   int quantidade,
                                   TipoMovimento tipoMovimento,
                                   String motivo) {
}