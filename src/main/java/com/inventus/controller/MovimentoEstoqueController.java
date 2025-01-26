package com.inventus.controller;

import com.inventus.domain.dto.movimento.MovimentoEstoqueDto;
import com.inventus.services.MovimentoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventus/estoque/movimentos")
public class MovimentoEstoqueController {

    @Autowired
    private MovimentoEstoqueService movimentoEstoqueService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<List<MovimentoEstoqueDto>> listar(@RequestHeader("Authorization") String token) {

        List<MovimentoEstoqueDto> movimentos = movimentoEstoqueService.listarMovimentos(token);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<MovimentoEstoqueDto> buscarID(@RequestHeader("Authorization") String token,
                                                        @PathVariable Long id) {

        MovimentoEstoqueDto movimento = movimentoEstoqueService.buscarMovimento(token, id);
        return ResponseEntity.ok(movimento);
    }

    @GetMapping("/buscar/produto/{id}")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<List<MovimentoEstoqueDto>> listarMovimentos(@RequestHeader("Authorization") String token,
                                                                      @PathVariable Long id) {

        List<MovimentoEstoqueDto> movimento = movimentoEstoqueService.listarMovimentosProduto(token, id);
        return ResponseEntity.ok(movimento);
    }
}