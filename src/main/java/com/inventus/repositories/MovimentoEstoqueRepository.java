package com.inventus.repositories;

import com.inventus.domain.movimento.MovimentoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {
}
