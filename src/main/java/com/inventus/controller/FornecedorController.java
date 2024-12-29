package com.inventus.controller;

import com.inventus.domain.dto.CadastrarFornecedorDto;
import com.inventus.domain.dto.FornecedorDto;
import com.inventus.services.FornecedorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventus/estoque/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FornecedorDto> cadastrar(@RequestHeader("Authorization") String token,
                                                   @RequestBody CadastrarFornecedorDto cadastrarFornecedorDto) {

        FornecedorDto fornecedor = fornecedorService.cadastrarFornecedor(token, cadastrarFornecedorDto);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(fornecedor);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<List<FornecedorDto>> listar(@RequestHeader("Authorization") String token) {

        List<FornecedorDto> fornecedores = fornecedorService.listarFornecedores(token);
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<FornecedorDto> buscarID(@RequestHeader("Authorization") String token,
                                                  @PathVariable Long id) {

        FornecedorDto fornecedor = fornecedorService.buscarFornecedor(token, id);
        return ResponseEntity.ok(fornecedor);
    }
}