package com.inventus.services.impl;

import com.inventus.domain.categoria.Categoria;
import com.inventus.domain.dto.produto.CadastrarProdutoDto;
import com.inventus.domain.dto.produto.ProdutoDto;
import com.inventus.domain.dto.produto.QuantidadeProdutoDto;
import com.inventus.domain.fornecedor.Fornecedor;
import com.inventus.domain.movimento.TipoMovimento;
import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.CategoriaNaoEncontradaException;
import com.inventus.infra.exception.FornecedorNaoEncontradoException;
import com.inventus.infra.exception.ProdutoNaoEncontradoException;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.CategoriaRepository;
import com.inventus.repositories.FornecedorRepository;
import com.inventus.repositories.ProdutoRepository;
import com.inventus.services.MovimentoEstoqueService;
import com.inventus.services.ProdutoService;
import com.inventus.services.validations.ValidarCadastroProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private ValidarCadastroProduto validarCadastroProduto;

    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public ProdutoDto cadastrarProduto(String token, CadastrarProdutoDto cadastrarProdutoDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        validarCadastroProduto.validar(usuario, cadastrarProdutoDto);

        Categoria categoria = categoriaRepository
                .findById(cadastrarProdutoDto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("A categoria informada não está registrada no sistema."));

        Fornecedor fornecedor = fornecedorRepository
                .findById(cadastrarProdutoDto.fornecedorId())
                .orElseThrow(() -> new FornecedorNaoEncontradoException("O fornecedor informado não está registrado no sistema."));

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

        movimentoEstoqueService.registrarMovimento(
                usuario,
                novoProduto,
                cadastrarProdutoDto.quantidade(),
                cadastrarProdutoDto.tipoMovimento(),
                cadastrarProdutoDto.motivo()
        );

        return new ProdutoDto(novoProduto);
    }

    @Override
    public List<ProdutoDto> listarProdutos(String token) {

        tokenService.getUserFromToken(token);

        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new ProdutoNaoEncontradoException("O estoque não possui produtos registrados no momento.");
        }

        return produtos.stream()
                .map(ProdutoDto::new)
                .toList();
    }

    @Override
    public ProdutoDto buscarProduto(String token, Long id) {

        tokenService.getUserFromToken(token);

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Nenhum produto registrado corresponde à busca."));
        return new ProdutoDto(produto);
    }

    @Override
    public ProdutoDto atualizarQuantidadeProduto(String token, QuantidadeProdutoDto quantidadeProdutoDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        Produto produto = produtoRepository.findById(quantidadeProdutoDto.id())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));

        if (quantidadeProdutoDto.tipoMovimento() == TipoMovimento.ENTRADA) {
            produto.entradaEstoque(quantidadeProdutoDto.quantidade());
        } else {
            produto.saidaEstoque(quantidadeProdutoDto.quantidade());
        }

        Produto produtoAtualizado = produtoRepository.save(produto);

        movimentoEstoqueService.registrarMovimento(
                usuario,
                produtoAtualizado,
                quantidadeProdutoDto.quantidade(),
                quantidadeProdutoDto.tipoMovimento(),
                quantidadeProdutoDto.motivo()
        );

        return new ProdutoDto(produtoAtualizado);
    }
}