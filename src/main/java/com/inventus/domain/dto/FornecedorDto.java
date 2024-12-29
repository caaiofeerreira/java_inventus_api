package com.inventus.domain.dto;

import com.inventus.domain.fornecedor.Fornecedor;

public record FornecedorDto(Long id,
                            String nome,
                            String cnpj,
                            String endereco,
                            String telefone,
                            String email) {

    public FornecedorDto(Fornecedor fornecedor) {
        this(fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getEmail());
    }
}