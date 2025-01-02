package com.inventus.services;

import com.inventus.domain.dto.fornecedor.CadastrarFornecedorDto;
import com.inventus.domain.dto.fornecedor.FornecedorDto;

import java.util.List;

public interface FornecedorService {

    FornecedorDto cadastrarFornecedor(String token, CadastrarFornecedorDto cadastrarFornecedorDto);

    List<FornecedorDto> listarFornecedores(String token);

    FornecedorDto buscarFornecedor(String token, Long id);
}