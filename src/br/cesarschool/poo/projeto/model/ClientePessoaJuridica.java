package br.cesarschool.poo.projeto.model;

public class ClientePessoaJuridica extends Cliente{
	
	private String cnpj;
	
	@Override
    public String getDocumento(){

        return cnpj;

    }

    @Override
    public String getPerfil(){

        return "Pessoa Jurídica";

    }

    public ClientePessoaJuridica(int id,
                        String nome,
                        String email,
                        String telefone,
                        String status,
                        String dataCadastro,
                        String cnpj){

        super(id,
              nome,
              email,
              telefone,
              status,
              dataCadastro);
        
        this.cnpj = cnpj;
        		
    }

    public String getCnpj() {
    	return cnpj;
    }
    
    public void setCnpj(String cnpj) {
    	this.cnpj = cnpj;
    }

    

}