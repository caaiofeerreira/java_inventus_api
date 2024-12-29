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
    public ProdutoDto cadastrarProduto(String token, CadastrarProdutoDto cadastrarProdutoDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }

        Produto produto = new Produto();
        produto.setUsuario(usuario);
        produto.setNome(cadastrarProdutoDto.nome());
        produto.setCodigoProduto(cadastrarProdutoDto.codigoProduto());
        produto.setQuantidade(cadastrarProdutoDto.quantidade());
        produto.setUnidadeMedida(cadastrarProdutoDto.unidadeMedida());
        produto.setPrecoCompra(cadastrarProdutoDto.precoCompra());
        produto.setDescricao(cadastrarProdutoDto.descricao());

        Categoria categoria = categoriaRepository
                .findById(cadastrarProdutoDto.categoriaId())
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada."));


        Fornecedor fornecedor = fornecedorRepository
                .findById(cadastrarProdutoDto.fornecedorId())
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado."));

        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);
        produto.setDataCadastro(LocalDate.now());
        produto.setStatus(Status.ATIVO);

        Produto novoProduto = produtoRepository.save(produto);

        movimentoEstoqueService.registrarEntrada(
                usuario,
                novoProduto,
                cadastrarProdutoDto.quantidade(),
                cadastrarProdutoDto.motivo());

        return new ProdutoDto(novoProduto);
    }
}