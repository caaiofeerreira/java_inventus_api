package com.inventus.controller;

import com.inventus.infra.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<ErrorResponse> handlerAutenticacao(AutenticacaoException autenticacaoException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                autenticacaoException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErrorResponse> handlerCredenciaisInvalidas(CredenciaisInvalidasException credenciaisInvalidasException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                credenciaisInvalidasException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AcessoRestritoException.class)
    public ResponseEntity<ErrorResponse> handlerAcessoRestrito(AcessoRestritoException acessoRestritoException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                acessoRestritoException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(FornecedorNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handlerFornecedorNaoEncontrado(FornecedorNaoEncontradoException fornecedorNaoEncontradoException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                fornecedorNaoEncontradoException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handlerCategoriaNaoEncontrada(CategoriaNaoEncontradaException categoriaNaoEncontrada) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                categoriaNaoEncontrada.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handlerProdutoNaoEncontrado(ProdutoNaoEncontradoException produtoNaoEncontradoException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                produtoNaoEncontradoException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MovimentoEstoqueNaoEncontradoExeption.class)
    public ResponseEntity<ErrorResponse> handlerMovimentoEstoqueNaoEncontrado(MovimentoEstoqueNaoEncontradoExeption movimentoEstoqueNaoEncontradoExeption) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                movimentoEstoqueNaoEncontradoExeption.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ValidarCadastroException.class)
    public ResponseEntity<ErrorResponse> handlerValidarCadastro(ValidarCadastroException validarCadastroException) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                validarCadastroException.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}