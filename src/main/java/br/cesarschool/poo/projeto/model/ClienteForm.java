package br.cesarschool.poo.projeto.model;

public class ClienteForm {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String status;
    private String dataCadastro;
    private String perfil;
    private String documento;

    public static ClienteForm fromCliente(Cliente c) {
        ClienteForm form = new ClienteForm();
        form.id = c.getId();
        form.nome = c.getNome();
        form.email = c.getEmail();
        form.telefone = c.getTelefone();
        form.status = c.getStatus();
        form.dataCadastro = c.getDataCadastro();
        form.perfil = c.getPerfil();
        form.documento = c.getDocumento();
        return form;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
}
