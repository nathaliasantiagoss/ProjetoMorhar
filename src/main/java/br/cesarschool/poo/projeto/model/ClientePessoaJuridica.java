package br.cesarschool.poo.projeto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PJ")
public class ClientePessoaJuridica extends Cliente {

    private String cnpj;

    public ClientePessoaJuridica() {}

    public ClientePessoaJuridica(String nome, String email, String telefone,
                                 String status, String dataCadastro, String cnpj) {
        super(nome, email, telefone, status, dataCadastro);
        this.cnpj = cnpj;
    }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    @Override
    public String getDocumento() { return cnpj; }

    @Override
    public String getPerfil() { return "Pessoa Jurídica"; }
}
