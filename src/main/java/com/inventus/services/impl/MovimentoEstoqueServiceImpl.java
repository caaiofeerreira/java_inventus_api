package com.inventus.services.impl;

import com.inventus.domain.dto.MovimentoEstoqueDto;
import com.inventus.domain.movimento.MovimentoEstoque;
import com.inventus.domain.movimento.TipoMovimento;
import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;
import com.inventus.repositories.MovimentoEstoqueRepository;
import com.inventus.services.MovimentoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MovimentoEstoqueServiceImpl implements MovimentoEstoqueService {

    @Autowired
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Override
    @Transactional
    public void registrarEntrada(Usuario usuario, Produto produto, Integer quantidade, String motivo) {

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setUsuarioId(usuario);
        movimentoEstoque.setProduto(produto);
        movimentoEstoque.setQuantidade(quantidade);
        movimentoEstoque.setTipoMovimento(TipoMovimento.ENTRADA);
        movimentoEstoque.setDataMovimento(LocalDateTime.now());
        movimentoEstoque.setMotivo(motivo);

        movimentoEstoqueRepository.save(movimentoEstoque);

        new MovimentoEstoqueDto(movimentoEstoque);
    }
}
