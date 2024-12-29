package com.inventus.services;

import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;

public interface MovimentoEstoqueService {

    void registrarEntrada(Usuario usuario, Produto produto, Integer quantidade, String motivo);
}