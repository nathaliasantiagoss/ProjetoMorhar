package br.cesarschool.poo.projeto.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.cesarschool.poo.projeto.model.Cliente;
import br.cesarschool.poo.projeto.model.ClientePessoaFisica;
import br.cesarschool.poo.projeto.model.ClientePessoaJuridica;
import br.cesarschool.poo.projeto.service.ClienteService;

public class TelaUsuario extends JFrame{
	
	private static final long serialVersionUID = 1L;

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtDocumento;
    private JTextField txtDataCadastro;
    private JTextField txtBusca;

    private JComboBox<String> cbPerfil;
    private JComboBox<String> cbStatus;

    private JButton btnAdicionar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnBuscar;
    private JButton btnLimpar;

    private JTable tabela;

    private DefaultTableModel modelo;

    private ClienteService service;

    public TelaUsuario(){

        setTitle("Gerenciamento de Clientes");

        setSize(1000,700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        service = new ClienteService();

        service.carregarClientes();
        
        

        //ID
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20,20,80,25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(110,20,100,25);
        txtId.setEditable(false); 
        add(txtId);

        //Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20,60,80,25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(110,60,200,25);
        add(txtNome);

        //Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20,100,80,25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(110,100,200,25);
        add(txtEmail);
        
        //Telefone
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(20,140,80,25);
        add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(110,140,200,25);
        add(txtTelefone);

        //Perfil
        JLabel lblPerfil = new JLabel("Perfil:");
        lblPerfil.setBounds(350,20,80,25);
        add(lblPerfil);

        cbPerfil = new JComboBox<>();

        cbPerfil.addItem("Pessoa Física");
        cbPerfil.addItem("Pessoa Jurídica");
  

        cbPerfil.setBounds(420,20,120,25);

        add(cbPerfil);
        
        //Documento
        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setBounds(20,180,80,25);
        add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(110,180,200,25);
        add(txtDocumento);
        
        JLabel lblDataCadastro = new JLabel("Data Cadastro:");
        lblDataCadastro.setBounds(20,220,100,25);
        add(lblDataCadastro);

        txtDataCadastro = new JTextField();
        txtDataCadastro.setBounds(110,220,200,25);
        add(txtDataCadastro);

        //Status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(350,60,80,25);
        add(lblStatus);

        cbStatus = new JComboBox<>();

        cbStatus.addItem("Ativo");
        cbStatus.addItem("Inativo");
        cbStatus.addItem("Bloqueado");

        cbStatus.setBounds(420,60,120,25);

        add(cbStatus);

        //Botões CRUD

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(20,260,120,30);
        add(btnAdicionar);
        btnAdicionar.addActionListener(e -> adicionarCliente());

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(150,260,120,30);
        add(btnEditar);
        btnEditar.addActionListener(e -> editarCliente());

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(280,260,120,30);
        add(btnExcluir);
        btnExcluir.addActionListener(e -> excluirCliente());

        //Busca

        txtBusca = new JTextField();
        txtBusca.setBounds(20,320,150,25);
        add(txtBusca);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(180,320,100,25);
        add(btnBuscar);
        btnBuscar.addActionListener(e -> buscarCliente());

        //Limpar

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(420,260,120,30);
        add(btnLimpar);
        btnLimpar.addActionListener(e -> limparCampos());

        //Tabela

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Email");
        modelo.addColumn("Telefone");
        modelo.addColumn("Perfil");
        modelo.addColumn("Documento");
        modelo.addColumn("Status");
        modelo.addColumn("Data de Cadastro");

        tabela = new JTable(modelo);
        
        tabela.getSelectionModel().addListSelectionListener(e -> carregarCampos());

        JScrollPane scroll = new JScrollPane(tabela);

        scroll.setBounds(20,360,950,180);

        add(scroll);

        carregarTabela();

    }

    private void carregarTabela(){

        modelo.setRowCount(0);

        for(var c : service.listarClientes()){
        	

            modelo.addRow(new Object[]{

                c.getId(),
                c.getNome(),
                c.getEmail(),
                c.getTelefone(),
                c.getPerfil(),
                c.getDocumento(),
                c.getStatus(),
                c.getDataCadastro()

            });

        }

    }
    
    private void adicionarCliente(){
    	
    	if(txtNome.getText().isEmpty()){

            JOptionPane.showMessageDialog(this,"Nome obrigatório");

            return;

        }

    	int id = service.gerarProximoId();

        String nome = txtNome.getText();

        String email = txtEmail.getText();
        
        String telefone = txtTelefone.getText();

        String perfil = cbPerfil.getSelectedItem().toString();
        
        String documento = txtDocumento.getText();

        String status = cbStatus.getSelectedItem().toString();
        
        String dataCadastro = txtDataCadastro.getText();

        Cliente cliente = null;

        if(perfil.equals("Pessoa Física")){

            cliente = new ClientePessoaFisica(
                    id,
                    nome,
                    email,
                    telefone,
                    status,
                    dataCadastro,
                    documento
            );

        }

        if(perfil.equals("Pessoa Jurídica")){

            cliente = new ClientePessoaJuridica(
                    id,
                    nome,
                    email,
                    telefone,
                    status,
                    dataCadastro,
                    documento
            );

  

        }

        service.adicionarCliente(cliente);
        
        service.salvarClientes();

        carregarTabela();

        limparCampos();

    }
    
    private void limparCampos(){

    	txtId.setText(String.valueOf(service.gerarProximoId()));

        txtNome.setText("");

        txtEmail.setText("");
        
        txtTelefone.setText("");
        
        txtDocumento.setText("");
        
        txtDataCadastro.setText("");

        txtBusca.setText("");

        cbPerfil.setSelectedIndex(0);

        cbStatus.setSelectedIndex(0);

        tabela.clearSelection();

        carregarTabela();

        txtId.requestFocus();
        
        txtDocumento.setEditable(true);
        
        txtDataCadastro.setEditable(true);
        
        cbPerfil.setEnabled(true);

    }
    
    private void excluirCliente(){

        int linha = tabela.getSelectedRow();

        if(linha == -1){

            return;

        }

        int id = (int) modelo.getValueAt(linha, 0);

        service.excluirCliente(id);
        
        service.salvarClientes();

        carregarTabela();

    }
    
    private void carregarCampos(){

        int linha = tabela.getSelectedRow();

        if(linha != -1){

            txtId.setText(modelo.getValueAt(linha,0).toString());

            txtNome.setText(modelo.getValueAt(linha,1).toString());

            txtEmail.setText(modelo.getValueAt(linha,2).toString());
            
            txtTelefone.setText(modelo.getValueAt(linha,3).toString());

            cbPerfil.setSelectedItem(modelo.getValueAt(linha,4).toString());
            
            txtDocumento.setText(modelo.getValueAt(linha,5).toString());

            cbStatus.setSelectedItem(modelo.getValueAt(linha,6).toString());
            
            txtDataCadastro.setText(modelo.getValueAt(linha,7).toString());
            
            txtDocumento.setEditable(false);
            
            txtDataCadastro.setEditable(false);
            
            cbPerfil.setEnabled(false);

        }

    }
    
    private void editarCliente(){

        int linha = tabela.getSelectedRow();

        if(linha == -1){
        	
        	JOptionPane.showMessageDialog(null,"Selecione um cliente");

            return;

        }

        int id = Integer.parseInt(txtId.getText());

        String nome = txtNome.getText();

        String email = txtEmail.getText();
        
        String telefone = txtTelefone.getText();
        
     

        String status = cbStatus.getSelectedItem().toString();

        service.editarCliente(id,nome,email,telefone,status);
        
        service.salvarClientes();

        carregarTabela();
        
        JOptionPane.showMessageDialog(null,"Cliente atualizado");

    }
    
    private void buscarCliente(){

        String texto = txtBusca.getText();

        if(texto.isEmpty()){

            carregarTabela();

            return;

        }

        modelo.setRowCount(0);

        for(var c : service.buscarCliente(texto)){

            modelo.addRow(new Object[]{

        		 c.getId(),
                 c.getNome(),
                 c.getEmail(),
                 c.getTelefone(),
                 c.getPerfil(),
                 c.getDocumento(),
                 c.getStatus(),
                 c.getDataCadastro()            
              

            });

        }

        if(modelo.getRowCount() == 0){

            JOptionPane.showMessageDialog(this,"Nenhum usuário encontrado");

        }

    }
    



    public static void main(String[] args){

        new TelaUsuario().setVisible(true);

    }

}