package com.inventus.controller;

import com.inventus.domain.dto.CadastrarProdutoDto;
import com.inventus.domain.dto.ProdutoDto;
import com.inventus.services.ProdutoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventus/estoque/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoDto> cadastrar(@RequestHeader("Authorization") String token,
                                                @RequestBody CadastrarProdutoDto cadastrarProdutoDto) {

        ProdutoDto produtoDto = produtoService.cadastrarProduto(token, cadastrarProdutoDto);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(produtoDto);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<List<ProdutoDto>> listar(@RequestHeader("Authorization") String token) {

        List<ProdutoDto> produtos = produtoService.listarProdutos(token);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<ProdutoDto> buscarID(@RequestHeader("Authorization") String token,
                                               @PathVariable Long id) {

        ProdutoDto produto = produtoService.buscarProduto(token, id);
        return ResponseEntity.ok(produto);
    }
}