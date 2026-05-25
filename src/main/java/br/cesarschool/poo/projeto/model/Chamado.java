package br.cesarschool.poo.projeto.model;

import jakarta.persistence.*;

@Entity
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String clienteNome;
    private String endereco;
    @Column(length = 1000)
    private String descricao;
    private String status;
    private String prioridade;
    private String tecnico;
    private String equipe;
    private String dataAbertura;
    private String horaAbertura;
    private String ultimaAtualizacao;

    public Chamado() {}

    public Chamado(String numero, String clienteNome, String endereco, String descricao,
                   String status, String prioridade, String tecnico, String equipe,
                   String dataAbertura, String horaAbertura, String ultimaAtualizacao) {
        this.numero = numero;
        this.clienteNome = clienteNome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.tecnico = tecnico;
        this.equipe = equipe;
        this.dataAbertura = dataAbertura;
        this.horaAbertura = horaAbertura;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }

    public String getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(String dataAbertura) { this.dataAbertura = dataAbertura; }

    public String getHoraAbertura() { return horaAbertura; }
    public void setHoraAbertura(String horaAbertura) { this.horaAbertura = horaAbertura; }

    public String getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(String ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
}
