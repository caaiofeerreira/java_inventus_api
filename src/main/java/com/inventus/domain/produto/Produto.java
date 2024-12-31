package com.inventus.domain.produto;

import com.inventus.domain.categoria.Categoria;
import com.inventus.domain.fornecedor.Fornecedor;
import com.inventus.domain.status.Status;
import com.inventus.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name="Produto")
@Table(name="tb_produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String nome;

    @Column(name = "codigo_produto", unique = true)
    private String codigoProduto;

    private Integer quantidade;
    private String unidadeMedida;
    private BigDecimal precoCompra;
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static Produto criarProduto(Usuario usuario, String nome, String codigoProduto, Integer quantidade,
                                       String unidadeMedida, BigDecimal precoCompra, String descricao,
                                       Categoria categoria, Fornecedor fornecedor) {

        Produto produto = new Produto();
        produto.setUsuario(usuario);
        produto.setNome(nome);
        produto.setCodigoProduto(codigoProduto);
        produto.setQuantidade(quantidade);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setPrecoCompra(precoCompra);
        produto.setDescricao(descricao);
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);
        produto.setDataCadastro(LocalDate.now());
        produto.setStatus(Status.ATIVO);
        return produto;
    }
}