package br.cesarschool.poo.projeto;

import br.cesarschool.poo.projeto.model.Agendamento;
import br.cesarschool.poo.projeto.model.Chamado;
import br.cesarschool.poo.projeto.model.ClientePessoaFisica;
import br.cesarschool.poo.projeto.model.ClientePessoaJuridica;
import br.cesarschool.poo.projeto.model.Tecnico;
import br.cesarschool.poo.projeto.model.Usuario;
import br.cesarschool.poo.projeto.repository.UsuarioRepository;
import br.cesarschool.poo.projeto.service.AgendaService;
import br.cesarschool.poo.projeto.service.ChamadoService;
import br.cesarschool.poo.projeto.service.ClienteService;
import br.cesarschool.poo.projeto.service.TecnicoService;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private ChamadoService chamadoService;
    @Autowired private TecnicoService tecnicoService;
    @Autowired private ClienteService clienteService;
    @Autowired private AgendaService agendaService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            criarTecnicosEUsuarios();
            criarChamados();
            criarAgendamentos();
        }
        if (clienteService.listarTodos().isEmpty()) {
            criarClientes();
        }
    }

    private void criarTecnicosEUsuarios() {
        // Usuário admin
        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setPerfil("ADMIN");
        usuarioRepository.save(admin);

        // Técnico Carlos Silva
        Tecnico carlos = new Tecnico();
        carlos.setNome("Carlos Silva");
        carlos.setCpf("111.222.333-44");
        carlos.setEmail("carlos.silva@morhar.com.br");
        carlos.setTelefone("(81) 99111-2233");
        carlos.setEspecialidade("Automação");
        carlos.setStatus("Ativo");
        carlos.setDataCadastro("2024-01-10");
        tecnicoService.salvar(carlos);

        Usuario uCarlos = new Usuario();
        uCarlos.setUsername("carlos.silva");
        uCarlos.setSenha(passwordEncoder.encode("tecnico123"));
        uCarlos.setPerfil("TECNICO");
        uCarlos.setTecnico(carlos);
        usuarioRepository.save(uCarlos);

        // Técnico João Santos
        Tecnico joao = new Tecnico();
        joao.setNome("João Santos");
        joao.setCpf("222.333.444-55");
        joao.setEmail("joao.santos@morhar.com.br");
        joao.setTelefone("(81) 99222-3344");
        joao.setEspecialidade("Elétrica");
        joao.setStatus("Ativo");
        joao.setDataCadastro("2024-01-10");
        tecnicoService.salvar(joao);

        Usuario uJoao = new Usuario();
        uJoao.setUsername("joao.santos");
        uJoao.setSenha(passwordEncoder.encode("tecnico123"));
        uJoao.setPerfil("TECNICO");
        uJoao.setTecnico(joao);
        usuarioRepository.save(uJoao);

        // Técnico Pedro Lima
        Tecnico pedro = new Tecnico();
        pedro.setNome("Pedro Lima");
        pedro.setCpf("333.444.555-66");
        pedro.setEmail("pedro.lima@morhar.com.br");
        pedro.setTelefone("(81) 99333-4455");
        pedro.setEspecialidade("Redes");
        pedro.setStatus("Ativo");
        pedro.setDataCadastro("2024-01-10");
        tecnicoService.salvar(pedro);

        Usuario uPedro = new Usuario();
        uPedro.setUsername("pedro.lima");
        uPedro.setSenha(passwordEncoder.encode("tecnico123"));
        uPedro.setPerfil("TECNICO");
        uPedro.setTecnico(pedro);
        usuarioRepository.save(uPedro);
    }

    private void criarClientes() {
        // Pessoas Físicas
        clienteService.salvar(new ClientePessoaFisica(
            "Ricardo Andrade", "ricardo.andrade@email.com", "(81) 99100-2233",
            "Ativo", "10/01/2024", "123.456.789-00"
        ));
        clienteService.salvar(new ClientePessoaFisica(
            "Fernanda Costa", "fernanda.costa@email.com", "(81) 98200-3344",
            "Ativo", "15/01/2024", "234.567.890-11"
        ));
        clienteService.salvar(new ClientePessoaFisica(
            "Marcos Oliveira", "marcos.oliveira@email.com", "(81) 97300-4455",
            "Ativo", "20/02/2024", "345.678.901-22"
        ));
        clienteService.salvar(new ClientePessoaFisica(
            "Ana Paula Souza", "ana.souza@email.com", "(81) 96400-5566",
            "Inativo", "05/03/2024", "456.789.012-33"
        ));

        // Pessoas Jurídicas
        clienteService.salvar(new ClientePessoaJuridica(
            "Tech Solutions Ltda", "contato@techsolutions.com.br", "(81) 3200-1122",
            "Ativo", "08/01/2024", "12.345.678/0001-90"
        ));
        clienteService.salvar(new ClientePessoaJuridica(
            "Condomínio Jardim das Flores", "sindico@jardimdasflores.com.br", "(81) 3201-2233",
            "Ativo", "12/01/2024", "23.456.789/0001-01"
        ));
        clienteService.salvar(new ClientePessoaJuridica(
            "Comercial Center Recife", "adm@comercialcenter.com.br", "(81) 3202-3344",
            "Ativo", "18/02/2024", "34.567.890/0001-12"
        ));
        clienteService.salvar(new ClientePessoaJuridica(
            "Edifício Solar do Pina", "adm@solardopina.com.br", "(81) 3203-4455",
            "Ativo", "22/03/2024", "45.678.901/0001-23"
        ));
    }

    private void criarChamados() {
        chamadoService.salvar(new Chamado(
            "CH-2024-0156", "Residência Andrade",
            "Rua das Flores, 320 – Boa Viagem",
            "Solicitado instalação completa do painel de automação da área externa, incluindo sensores, câmeras e integração com o sistema existente.",
            "Em andamento", "Alta", "Carlos Silva", "Instalações",
            "20/05/2024", "09:15", "20/05/2024 às 14:30"
        ));
        chamadoService.salvar(new Chamado(
            "CH-2024-0155", "Edifício Solar do Pina",
            "Av. Boa Viagem, 1500 – Boa Viagem",
            "Falha na automação de iluminação do corredor B. Luzes não respondem ao sensor de presença.",
            "Aberto", "Média", "João Santos", "Manutenção",
            "20/05/2024", "08:30", "20/05/2024 às 08:30"
        ));
        chamadoService.salvar(new Chamado(
            "CH-2024-0154", "Empresa Tech Solutions",
            "Rua do Progresso, 80 – Afogados",
            "Manutenção preventiva anual dos sistemas de automação predial.",
            "Finalizado", "Baixa", "Carlos Silva", "Manutenção",
            "19/05/2024", "16:45", "19/05/2024 às 18:00"
        ));
        chamadoService.salvar(new Chamado(
            "CH-2024-0153", "Condomínio Jardim das Flores",
            "Rua Jardim das Flores, 500 – Recife",
            "Revisão do sistema de câmeras e atualização de firmware dos dispositivos.",
            "Aberto", "Alta", "Pedro Lima", "Instalações",
            "19/05/2024", "14:00", "19/05/2024 às 14:00"
        ));
        chamadoService.salvar(new Chamado(
            "CH-2024-0152", "Residência Boa Viagem",
            "Rua das Nações, 45 – Boa Viagem",
            "Problema no portão eletrônico – não responde ao comando do controle remoto.",
            "Em andamento", "Média", "João Santos", "Manutenção",
            "18/05/2024", "11:20", "18/05/2024 às 13:00"
        ));
        chamadoService.salvar(new Chamado(
            "CH-2024-0151", "Comercial Center Recife",
            "Av. Cruz Cabugá, 1000 – Santo Amaro",
            "Upgrade no sistema de automação predial com instalação de novos módulos.",
            "Finalizado", "Baixa", "Carlos Silva", "Instalações",
            "17/05/2024", "10:10", "17/05/2024 às 17:30"
        ));
    }

    private void criarAgendamentos() {
        String hoje     = LocalDate.now().toString();
        String amanha   = LocalDate.now().plusDays(1).toString();
        String semana   = LocalDate.now().plusDays(3).toString();
        String passado  = LocalDate.now().minusDays(2).toString();
        String passado2 = LocalDate.now().minusDays(5).toString();

        Agendamento a1 = new Agendamento();
        a1.setTitulo("Instalação de câmeras externas");
        a1.setClienteNome("Condomínio Jardim das Flores");
        a1.setEndereco("Rua Jardim das Flores, 500 – Recife");
        a1.setTecnico("Carlos Silva");
        a1.setDataAgendamento(hoje);
        a1.setHoraInicio("09:00");
        a1.setHoraFim("12:00");
        a1.setStatus("Confirmado");
        a1.setObservacao("Cliente solicitou chegada pontual. Portaria autorizada.");
        a1.setDataCadastro("15/05/2024");
        agendaService.salvar(a1);

        Agendamento a2 = new Agendamento();
        a2.setTitulo("Manutenção preventiva – painel de automação");
        a2.setClienteNome("Residência Andrade");
        a2.setEndereco("Rua das Flores, 320 – Boa Viagem");
        a2.setTecnico("João Santos");
        a2.setDataAgendamento(hoje);
        a2.setHoraInicio("14:00");
        a2.setHoraFim("17:00");
        a2.setStatus("Agendado");
        a2.setObservacao("Levar kit de ferramentas completo.");
        a2.setDataCadastro("15/05/2024");
        agendaService.salvar(a2);

        Agendamento a3 = new Agendamento();
        a3.setTitulo("Revisão de rede cabeada");
        a3.setClienteNome("Empresa Tech Solutions");
        a3.setEndereco("Rua do Progresso, 80 – Afogados");
        a3.setTecnico("Pedro Lima");
        a3.setDataAgendamento(amanha);
        a3.setHoraInicio("08:30");
        a3.setHoraFim("11:30");
        a3.setStatus("Agendado");
        a3.setObservacao("Verificar pontos de rede no 3º andar.");
        a3.setDataCadastro("15/05/2024");
        agendaService.salvar(a3);

        Agendamento a4 = new Agendamento();
        a4.setTitulo("Instalação de portão automático");
        a4.setClienteNome("Residência Boa Viagem");
        a4.setEndereco("Rua das Nações, 45 – Boa Viagem");
        a4.setTecnico("Carlos Silva");
        a4.setDataAgendamento(semana);
        a4.setHoraInicio("10:00");
        a4.setHoraFim("");
        a4.setStatus("Agendado");
        a4.setObservacao("");
        a4.setDataCadastro("14/05/2024");
        agendaService.salvar(a4);

        Agendamento a5 = new Agendamento();
        a5.setTitulo("Upgrade sistema de alarme");
        a5.setClienteNome("Edifício Solar do Pina");
        a5.setEndereco("Av. Boa Viagem, 1500 – Boa Viagem");
        a5.setTecnico("João Santos");
        a5.setDataAgendamento(passado);
        a5.setHoraInicio("13:00");
        a5.setHoraFim("15:00");
        a5.setStatus("Realizado");
        a5.setObservacao("Serviço concluído com sucesso.");
        a5.setDataCadastro("10/05/2024");
        agendaService.salvar(a5);

        Agendamento a6 = new Agendamento();
        a6.setTitulo("Vistoria pós-obra");
        a6.setClienteNome("Comercial Center Recife");
        a6.setEndereco("Av. Cruz Cabugá, 1000 – Santo Amaro");
        a6.setTecnico("Pedro Lima");
        a6.setDataAgendamento(passado2);
        a6.setHoraInicio("09:00");
        a6.setHoraFim("10:30");
        a6.setStatus("Cancelado");
        a6.setObservacao("Cliente remarcou para outra data.");
        a6.setDataCadastro("08/05/2024");
        agendaService.salvar(a6);
    }
}
