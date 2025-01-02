package com.inventus.repositories;

import com.inventus.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.nome = :nome")
    Optional<Produto> findByNomeProduto(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p WHERE p.codigoProduto = :codigo")
    Optional<Produto> findByCodigoProduto(@Param("codigo") String codigo);
}