package com.inventus.repositories;

import com.inventus.domain.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    @Query("SELECT f FROM Fornecedor f WHERE f.nome = :nome")
    Optional<Fornecedor> findByNomeFornecedor(@Param("nome") String nome);

    @Query("SELECT f FROM Fornecedor f WHERE f.cnpj = :cnpj")
    Optional<Fornecedor> findByCnpjFornecedor(@Param("cnpj") String cnpj);
}