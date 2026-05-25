package br.cesarschool.poo.projeto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PF")
public class ClientePessoaFisica extends Cliente {

    private String cpf;

    public ClientePessoaFisica() {}

    public ClientePessoaFisica(String nome, String email, String telefone,
                               String status, String dataCadastro, String cpf) {
        super(nome, email, telefone, status, dataCadastro);
        this.cpf = cpf;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String getDocumento() { return cpf; }

    @Override
    public String getPerfil() { return "Pessoa Física"; }
}
