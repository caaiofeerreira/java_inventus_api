package com.inventus.domain.categoria;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="Categoria")
@Table(name="tb_categoria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    public static Categoria criarCategoria(String nome, String descricao) {

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setDescricao(descricao);

        return categoria;
    }
}