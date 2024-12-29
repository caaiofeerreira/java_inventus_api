package com.inventus.domain.dto;

public record CadastrarFornecedorDto(String nome,
                                     String cnpj,
                                     String endereco,
                                     String telefone,
                                     String email) {
}