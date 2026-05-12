package br.cesarschool.poo.projeto.model;

public class ClientePessoaFisica extends Cliente{
	
	private String cpf;
	
	@Override
    	public String getDocumento(){

	        return cpf;

	    }

    @Override
	    public String getPerfil(){

	        return "Pessoa Física";

	    }

    public ClientePessoaFisica(int id,
                        String nome,
                        String email,
                        String telefone,
                        String status,
                        String dataCadastro,
                        String cpf){

        super(id,
              nome,
              email,
              telefone,
              status,
              dataCadastro);
        
        this.cpf = cpf;
        		
    }

    public String getCpf() {
    	return cpf;
    }
    
    public void setCpf(String cpf) {
    	this.cpf = cpf;
    }

    

}