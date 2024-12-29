package com.inventus.services.impl;

import com.inventus.domain.dto.CadastrarFornecedorDto;
import com.inventus.domain.dto.FornecedorDto;
import com.inventus.domain.fornecedor.Fornecedor;
import com.inventus.domain.status.Status;
import com.inventus.domain.usuario.UserRole;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.AcessoRestritoException;
import com.inventus.infra.exception.FornecedorNaoEncontradoException;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.FornecedorRepository;
import com.inventus.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    @Transactional
    public FornecedorDto cadastrarFornecedor(String token, CadastrarFornecedorDto cadastrarFornecedorDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        if (usuario.getUserRole() != UserRole.ADMIN) {
            throw new AcessoRestritoException("Apenas ADMIN podem acessar esse serviço.");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(cadastrarFornecedorDto.nome());
        fornecedor.setCnpj(cadastrarFornecedorDto.cnpj());
        fornecedor.setEndereco(cadastrarFornecedorDto.endereco());
        fornecedor.setEmail(cadastrarFornecedorDto.email());
        fornecedor.setTelefone(cadastrarFornecedorDto.telefone());
        fornecedor.setDataCadastro(LocalDate.now());
        fornecedor.setStatus(Status.ATIVO);

        fornecedorRepository.save(fornecedor);

        return new FornecedorDto(fornecedor);
    }

    @Override
    public List<FornecedorDto> listarFornecedores(String token) {

        tokenService.getUserFromToken(token);

        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        if (fornecedores.isEmpty()) {
            throw new FornecedorNaoEncontradoException("Lista de Fornecedores esta vazia.");
        }

        return fornecedores.stream()
                .map(FornecedorDto::new)
                .toList();
    }

    @Override
    public FornecedorDto buscarFornecedor(String token, Long id) {

        tokenService.getUserFromToken(token);

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Fornecedor não encontrado."));

        return new FornecedorDto(fornecedor);
    }
}
