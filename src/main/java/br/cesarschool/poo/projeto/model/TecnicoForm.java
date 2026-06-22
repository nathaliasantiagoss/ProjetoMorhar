package br.cesarschool.poo.projeto.model;

public class TecnicoForm {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String especialidade;
    private String status;
    private String dataCadastro;
    private String senhaCadastro;

    public static TecnicoForm fromTecnico(Tecnico t) {
        TecnicoForm form = new TecnicoForm();
        form.id = t.getId();
        form.nome = t.getNome();
        form.cpf = t.getCpf();
        form.email = t.getEmail();
        form.telefone = t.getTelefone();
        form.especialidade = t.getEspecialidade();
        form.status = t.getStatus();
        form.dataCadastro = t.getDataCadastro();
        return form;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getSenhaCadastro() { return senhaCadastro; }
    public void setSenhaCadastro(String senhaCadastro) { this.senhaCadastro = senhaCadastro; }
}
