package com.inventus.services.impl;

import com.inventus.domain.categoria.Categoria;
import com.inventus.domain.dto.CadastrarProdutoDto;
import com.inventus.domain.dto.ProdutoDto;
import com.inventus.domain.fornecedor.Fornecedor;
import com.inventus.domain.produto.Produto;
import com.inventus.domain.status.Status;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.CategoriaNaoEncontradaException;
import com.inventus.infra.exception.FornecedorNaoEncontradoException;
import com.inventus.infra.exception.ProdutoNaoEncontradoException;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.CategoriaRepository;
import com.inventus.repositories.FornecedorRepository;
import com.inventus.repositories.ProdutoRepository;
import com.inventus.services.MovimentoEstoqueService;
import com.inventus.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private MovimentoEstoqueService movimentoEstoqueService;

    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public ProdutoDto cadastrarProduto(String token, CadastrarProdutoDto cadastrarProdutoDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }

        Categoria categoria = categoriaRepository
                .findById(cadastrarProdutoDto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada."));

        Fornecedor fornecedor = fornecedorRepository
                .findById(cadastrarProdutoDto.fornecedorId())
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado."));

        Produto produto = Produto.criarProduto(
                usuario,
                cadastrarProdutoDto.nome(),
                cadastrarProdutoDto.codigoProduto(),
                cadastrarProdutoDto.quantidade(),
                cadastrarProdutoDto.unidadeMedida(),
                cadastrarProdutoDto.precoCompra(),
                cadastrarProdutoDto.descricao(),
                categoria,
                fornecedor
        );

        Produto novoProduto = produtoRepository.save(produto);

        movimentoEstoqueService.registrarEntrada(
                usuario,
                novoProduto,
                cadastrarProdutoDto.quantidade(),
                cadastrarProdutoDto.motivo()
        );

        return new ProdutoDto(novoProduto);
    }

    @Override
    public List<ProdutoDto> listarProdutos(String token) {

        tokenService.getUserFromToken(token);

        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Lista de produtos esta vazia.");
        }

        return produtos.stream()
                .map(ProdutoDto::new)
                .toList();
    }

    @Override
    public ProdutoDto buscarProduto(String token, Long id) {

        tokenService.getUserFromToken(token);

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto nao encontrado."));
        return new ProdutoDto(produto);
    }
}