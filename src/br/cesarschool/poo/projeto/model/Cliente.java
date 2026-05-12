package br.cesarschool.poo.projeto.model;

public abstract class Cliente {
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String status;
	private String dataCadastro;
	

	
	public Cliente(int id, String nome, String email, String telefone, String status, String dataCadastro) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
        this.status = status;
        this.dataCadastro = dataCadastro;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
        this.id=id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome=nome;
    }
    
    

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
    
    public String getTelefone() {
    	return telefone;
    }
    
    public void setTelefone(String telefone) {
    	this.telefone = telefone;
    }


    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getDataCadastro(){
        return dataCadastro;
    }

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
    public abstract String getDocumento();

    public abstract String getPerfil();


}
