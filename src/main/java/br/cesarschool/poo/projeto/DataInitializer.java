package br.cesarschool.poo.projeto;

import br.cesarschool.poo.projeto.model.Chamado;
import br.cesarschool.poo.projeto.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ChamadoService chamadoService;

    @Override
    public void run(String... args) {
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
}
