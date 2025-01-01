package com.inventus.domain.fornecedor;

import com.inventus.domain.status.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name="Fornecedor")
@Table(name="tb_fornecedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static Fornecedor criarFornecedor(String nome, String cnpj, String endereco, String telefone,
                                             String email, LocalDate dataCadastro, Status status) {

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(nome);
        fornecedor.setCnpj(cnpj);
        fornecedor.setEndereco(endereco);
        fornecedor.setTelefone(telefone.replace(" ", ""));
        fornecedor.setEmail(email);
        fornecedor.setDataCadastro(dataCadastro);
        fornecedor.setStatus(status);

        return fornecedor;
    }
}