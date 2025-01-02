package com.inventus.services.validations;

import com.inventus.domain.dto.produto.CadastrarProdutoDto;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.ValidarCadastroException;
import com.inventus.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidarCadastroProduto {

    @Autowired
    private ProdutoRepository produtoRepository;

    private static final String NOME_REGEX = "[A-Za-z0-9 ]+";

    public void validar(Usuario usuario, CadastrarProdutoDto cadastrarProdutoDto) {

        validarUsuario(usuario);
        validarNome(cadastrarProdutoDto);
        validarCodigoProduto(cadastrarProdutoDto);
        validarQuantidade(cadastrarProdutoDto);
        validarUnidadeMedida(cadastrarProdutoDto);
        validarPrecoCompra(cadastrarProdutoDto);
        validarDescricao(cadastrarProdutoDto);
        validarMotivo(cadastrarProdutoDto);

    }

    private void validarUsuario(Usuario usuario) {

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }
    }

    private void validarNome(CadastrarProdutoDto cadastrarProdutoDto) {

        String nome = cadastrarProdutoDto.nome().trim().toLowerCase();

        if (nome.isEmpty() || !nome.matches(NOME_REGEX)) {
            throw new ValidarCadastroException("O nome do produto é obrigatório e deve conter apenas letras, números e espaços.");
        }

        produtoRepository.findByNomeProduto(nome)
                .ifPresent(nomeProduto -> {
                    throw new ValidarCadastroException("O nome do produto sugerido já possui cadastro.");
                });
    }

    private void validarCodigoProduto(CadastrarProdutoDto cadastrarProdutoDto) {

        String codigo = cadastrarProdutoDto.codigoProduto().trim().toLowerCase();

        if (codigo.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o codigo do produto. Este campo é obrigatório.");
        }

        produtoRepository.findByCodigoProduto(codigo)
                .ifPresent(codigoProduto ->{
                    throw new ValidarCadastroException("O codigo do produto sugerido já possui cadastro.");
                });

    }

    private void validarQuantidade(CadastrarProdutoDto cadastrarProdutoDto) {

        Integer quantidade = cadastrarProdutoDto.quantidade();

        if (quantidade == null || quantidade <= 0) {
            throw new ValidarCadastroException("A quantidade do produto não pode ser vazio, zero ou negativo. Por favor, forneça um valor maior que zero.");
        }
    }

    private void validarUnidadeMedida(CadastrarProdutoDto cadastrarProdutoDto) {

        String unidadeMedida = cadastrarProdutoDto.unidadeMedida().trim();

        if (unidadeMedida.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça a unidade de medida do produto. Este campo é obrigatório.");
        }
    }

    private void validarPrecoCompra(CadastrarProdutoDto cadastrarProdutoDto) {

        BigDecimal preco = cadastrarProdutoDto.precoCompra();

        if (preco == null ||  preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidarCadastroException("O preço do produto não pode ser vazio, zero ou negativo. Por favor, forneça um valor maior que zero.");
        }
    }

    private void validarDescricao(CadastrarProdutoDto cadastrarProdutoDto) {

        String descricao = cadastrarProdutoDto.descricao().trim();

        if (descricao.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça a descriçao do produto. Este campo é obrigatório.");
        }
    }

    private void validarMotivo(CadastrarProdutoDto cadastrarProdutoDto) {

        String motivo = cadastrarProdutoDto.motivo().trim();

        if (motivo.isEmpty()) {
            throw new ValidarCadastroException("Por favor, forneça o motivo do produto. Este campo é obrigatório.");
        }
    }
}