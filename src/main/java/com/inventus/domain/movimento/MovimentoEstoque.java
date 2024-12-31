package com.inventus.domain.movimento;

import com.inventus.domain.produto.Produto;
import com.inventus.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name="MovimentoEstoque")
@Table(name="tb_movimento_estoque")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private TipoMovimento tipoMovimento;

    private LocalDateTime dataMovimento;
    private String motivo;

    public static MovimentoEstoque criarMovimento(Usuario usuario, Produto produto, Integer quantidade,
                                                  TipoMovimento tipoMovimento, LocalDateTime dataMovimento, String motivo) {

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setUsuarioId(usuario);
        movimentoEstoque.setProduto(produto);
        movimentoEstoque.setQuantidade(quantidade);
        movimentoEstoque.setTipoMovimento(tipoMovimento);
        movimentoEstoque.setDataMovimento(dataMovimento);
        movimentoEstoque.setMotivo(motivo);

        return movimentoEstoque;
    }
}