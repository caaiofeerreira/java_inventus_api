package com.inventus.services.impl;

import com.inventus.domain.dto.fornecedor.CadastrarFornecedorDto;
import com.inventus.domain.dto.fornecedor.FornecedorDto;
import com.inventus.domain.fornecedor.Fornecedor;
import com.inventus.domain.status.Status;
import com.inventus.domain.usuario.Usuario;
import com.inventus.infra.exception.FornecedorNaoEncontradoException;
import com.inventus.infra.security.TokenService;
import com.inventus.repositories.FornecedorRepository;
import com.inventus.services.FornecedorService;
import com.inventus.services.validations.ValidarCadastroFornecedor;
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

    @Autowired
    private ValidarCadastroFornecedor validarFornecedor;

    @Override
    @Transactional
    public FornecedorDto cadastrarFornecedor(String token, CadastrarFornecedorDto cadastrarFornecedorDto) {

        Usuario usuario = tokenService.getUserFromToken(token);

        validarFornecedor.validar(usuario, cadastrarFornecedorDto);

        Fornecedor fornecedor = Fornecedor.criarFornecedor(
                cadastrarFornecedorDto.nome(),
                cadastrarFornecedorDto.cnpj(),
                cadastrarFornecedorDto.endereco(),
                cadastrarFornecedorDto.telefone(),
                cadastrarFornecedorDto.email(),
                LocalDate.now(),
                Status.ATIVO
        );

        fornecedorRepository.save(fornecedor);

        return new FornecedorDto(fornecedor);
    }

    @Override
    public List<FornecedorDto> listarFornecedores(String token) {

        tokenService.getUserFromToken(token);

        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        if (fornecedores.isEmpty()) {
            throw new FornecedorNaoEncontradoException("O estoque não possui fornecedores registrados no momento.");
        }

        return fornecedores.stream()
                .map(FornecedorDto::new)
                .toList();
    }

    @Override
    public FornecedorDto buscarFornecedor(String token, Long id) {

        tokenService.getUserFromToken(token);

        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNaoEncontradoException("Nenhum fornecedor registrado corresponde à busca"));

        return new FornecedorDto(fornecedor);
    }
}