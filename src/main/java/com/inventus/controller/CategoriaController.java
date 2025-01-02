package com.inventus.controller;

import com.inventus.domain.dto.categoria.CadastrarCategoriaDto;
import com.inventus.domain.dto.categoria.CategoriaDto;
import com.inventus.services.CategoriaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventus/estoque/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDto> cadastrar(@RequestHeader("Authorization") String token,
                                                  @RequestBody CadastrarCategoriaDto cadastrarCategoriaDto) {

        CategoriaDto categoria = categoriaService.cadastrarCategoria(token, cadastrarCategoriaDto);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(categoria);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<List<CategoriaDto>> listarCategorias(@RequestHeader("Authorization") String token) {

        List<CategoriaDto> categorias = categoriaService.listarCategorias(token);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN','SUPERVISOR', 'FUNCIONARIO')")
    public ResponseEntity<CategoriaDto> buscarID(@RequestHeader("Authorization") String token,
                                                 @PathVariable Long id) {

        CategoriaDto categoria = categoriaService.buscarCategoria(token, id);
        return ResponseEntity.ok(categoria);
    }
}