package com.inventus.services.impl;

import com.inventus.domain.dto.movimento.MovimentoEstoqueDto;
import com.inventus.domain.movimento.MovimentoEstoque;
import com.inventus.domain.movimento.TipoMovimento;
import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.MovimentoEstoqueNaoEncontradoExeption;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.MovimentoEstoqueRepository;
import com.inventus.services.MovimentoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentoEstoqueServiceImpl implements MovimentoEstoqueService {

    @Autowired
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public void registrarMovimento(Usuario usuario, Produto produto, int quantidade, TipoMovimento tipoMovimento, String motivo) {

        MovimentoEstoque movimentoEstoque = MovimentoEstoque.criarMovimento(
                usuario,
                produto,
                quantidade,
                tipoMovimento,
                LocalDateTime.now(),
                motivo
        );

        movimentoEstoqueRepository.save(movimentoEstoque);

        new MovimentoEstoqueDto(movimentoEstoque);
    }

    @Override
    public List<MovimentoEstoqueDto> listarMovimentos(String token) {

        tokenService.getUserFromToken(token);

        List<MovimentoEstoque> movimentos = movimentoEstoqueRepository.findAll();

        if (movimentos.isEmpty()) {
            throw new MovimentoEstoqueNaoEncontradoExeption("O estoque não possui movimentações registradas no momento.");
        }

        return movimentos.stream()
                .map(MovimentoEstoqueDto::new)
                .toList();
    }

    @Override
    public MovimentoEstoqueDto buscarMovimento(String token, Long id) {

        tokenService.getUserFromToken(token);

        MovimentoEstoque movimento = movimentoEstoqueRepository.findById(id)
                .orElseThrow(() -> new MovimentoEstoqueNaoEncontradoExeption("Nenhuma movimentação registrada corresponde à busca."));

        return new MovimentoEstoqueDto(movimento);
    }
}