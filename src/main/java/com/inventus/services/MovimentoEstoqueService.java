package com.inventus.services;

import com.inventus.domain.dto.movimento.MovimentoEstoqueDto;
import com.inventus.domain.movimento.TipoMovimento;
import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;

import java.util.List;

public interface MovimentoEstoqueService {

    void registrarMovimento(Usuario usuario, Produto produto, int quantidade, TipoMovimento tipoMovimento, String motivo);

    List<MovimentoEstoqueDto> listarMovimentos(String token);

    MovimentoEstoqueDto buscarMovimento(String token, Long id);

    List<MovimentoEstoqueDto> listarMovimentosProduto(String token, Long id);
}