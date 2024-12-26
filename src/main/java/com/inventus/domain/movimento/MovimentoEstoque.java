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
}