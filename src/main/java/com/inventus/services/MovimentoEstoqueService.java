package com.inventus.services;

import com.inventus.domain.dto.movimento.MovimentoEstoqueDto;
import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;

import java.util.List;

public interface MovimentoEstoqueService {

    void registrarEntrada(Usuario usuario, Produto produto, Integer quantidade, String motivo);

    List<MovimentoEstoqueDto> listarMovimentos(String token);

    MovimentoEstoqueDto buscarMovimento(String token, Long id);
}