package br.cesarschool.poo.projeto.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.cesarschool.poo.projeto.model.Cliente;
import br.cesarschool.poo.projeto.model.ClientePessoaFisica;
import br.cesarschool.poo.projeto.model.ClientePessoaJuridica;


public class ClienteService {

    private List<Cliente> clientes;

    //Construtor
    public ClienteService(){

        clientes = new ArrayList<>();

    }

    //Adicionar cliente
    public void adicionarCliente(Cliente cliente){

        clientes.add(cliente);

    }
    
    public int gerarProximoId(){

        int maior = 0;

        for(Cliente c : clientes){

            if(c.getId() > maior){

                maior = c.getId();

            }

        }

        return maior + 1;

    }

    //Listar clientes
    public List<Cliente> listarClientes(){

        return clientes;

    }

    //Excluir cliente
    public void excluirCliente(int id){

        clientes.removeIf(c -> c.getId() == id);

    }

    //Buscar cliente
    public List<Cliente> buscarCliente(String texto){

        List<Cliente> resultado = new ArrayList<>();

        for(Cliente c : clientes){

            if(c.getNome().toLowerCase().contains(texto.toLowerCase())){

                resultado.add(c);

            }

        }

        return resultado;

    }
    
    public Cliente buscarPorId(int id){

        for(Cliente c : clientes){

            if(c.getId() == id){

                return c;

            }

        }

        return null;

    }

    //Editar cliente
    public void editarCliente(int id,
                              String nome,
                              String email,
                              String telefone,
                              String status){

        for(Cliente c : clientes){

            if(c.getId() == id){

                c.setNome(nome);

                c.setEmail(email);
                
                c.setTelefone(telefone);

                c.setStatus(status);

            }

        }

    }

    //Limpar lista
    public void limparClientes(){

        clientes.clear();

    }
    
    public void salvarClientes(){

        try{

            PrintWriter writer = new PrintWriter("clientes.txt");

            for(Cliente c : clientes){

                writer.println(
                    c.getId()+";"+
                    c.getNome()+";"+
                    c.getEmail()+";"+
                    c.getTelefone()+";"+
                    c.getPerfil()+";"+
                    c.getDocumento()+";"+
                    c.getStatus()+";"+
                    c.getDataCadastro()
                );

            }

            writer.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }
    
    public void carregarClientes(){

        try{

            File file = new File("clientes.txt");

            if(!file.exists()) return;

            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()){

                String linha = sc.nextLine();

                String dados[] = linha.split(";");

                int id = Integer.parseInt(dados[0]);

                Cliente c;

                if(dados[4].equals("Pessoa Física")){

                    c = new ClientePessoaFisica(
                        id,
                        dados[1],
                        dados[2],
                        dados[3],
                        dados[6],
                        dados[7],
                        dados[5]
                    );

                }else{

                    c = new ClientePessoaJuridica(
                        id,
                        dados[1],
                        dados[2],
                        dados[3],
                        dados[6],
                        dados[7],
                        dados[5]
                    );

                }

                clientes.add(c);

            }

            sc.close();

        }catch(Exception e){

            e.printStackTrace();

        }

    }

    //Carregar clientes iniciais
    public void carregarClientesMock(){

        clientes.add(
            new ClientePessoaFisica(
                1,
                "Ana Silva",
                "ana@email.com",
                "(81) 99856-9854",
                "Ativo",
                "01/01/2025",
                "105.458.874-00"
            )
        );

        clientes.add(
            new ClientePessoaJuridica(
                2,
                "Carlos Souza",
                "carlos@email.com",
                "(81) 98456-2052",
                "Ativo",
                "02/01/2025",
                "18.191.583/0001-40"
            )
        );

        

    }

}