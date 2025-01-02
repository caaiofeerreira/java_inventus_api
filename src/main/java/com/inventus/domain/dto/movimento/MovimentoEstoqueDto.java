package com.inventus.domain.dto.movimento;

import com.inventus.domain.movimento.MovimentoEstoque;
import com.inventus.domain.movimento.TipoMovimento;

import java.time.LocalDateTime;

public record MovimentoEstoqueDto(Long id,
                                  Integer quantidade,
                                  TipoMovimento tipoMovimento,
                                  LocalDateTime dataMovimento,
                                  String motivo) {

    public MovimentoEstoqueDto(MovimentoEstoque movimentoEstoque) {
        this(movimentoEstoque.getId(),
                movimentoEstoque.getQuantidade(),
                movimentoEstoque.getTipoMovimento(),
                movimentoEstoque.getDataMovimento(),
                movimentoEstoque.getMotivo());
    }
}