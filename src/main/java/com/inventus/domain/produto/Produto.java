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
}