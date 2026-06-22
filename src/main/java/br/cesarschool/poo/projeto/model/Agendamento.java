package br.cesarschool.poo.projeto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String clienteNome;
    private String endereco;
    private String tecnico;
    private String dataAgendamento; // yyyy-MM-dd (HTML date input)
    private String horaInicio;      // HH:mm
    private String horaFim;         // HH:mm (opcional)
    private String status;          // Agendado, Confirmado, Realizado, Cancelado
    @Column(length = 1000)
    private String observacao;
    private String dataCadastro;
    private Long chamadoId;         // opcional: agendamento gerado a partir de um chamado

    public Agendamento() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public String getDataAgendamento() { return dataAgendamento; }
    public void setDataAgendamento(String dataAgendamento) { this.dataAgendamento = dataAgendamento; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFim() { return horaFim; }
    public void setHoraFim(String horaFim) { this.horaFim = horaFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }

    public Long getChamadoId() { return chamadoId; }
    public void setChamadoId(Long chamadoId) { this.chamadoId = chamadoId; }

    // yyyy-MM-dd → dd/MM/yyyy para exibição
    public String getDataFormatada() {
        if (dataAgendamento == null || dataAgendamento.length() != 10) return dataAgendamento;
        return dataAgendamento.substring(8) + "/" + dataAgendamento.substring(5, 7) + "/" + dataAgendamento.substring(0, 4);
    }
}
