package br.cesarschool.poo.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "anexo")
public class Anexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chamadoId;
    private String nomeArquivo;
    private String dataUpload;

    public Anexo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getChamadoId() { return chamadoId; }
    public void setChamadoId(Long chamadoId) { this.chamadoId = chamadoId; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getDataUpload() { return dataUpload; }
    public void setDataUpload(String dataUpload) { this.dataUpload = dataUpload; }
}
