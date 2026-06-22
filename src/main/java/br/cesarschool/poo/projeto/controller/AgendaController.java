package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.Agendamento;
import br.cesarschool.poo.projeto.model.Chamado;
import br.cesarschool.poo.projeto.model.Usuario;
import br.cesarschool.poo.projeto.repository.TecnicoRepository;
import br.cesarschool.poo.projeto.repository.UsuarioRepository;
import br.cesarschool.poo.projeto.service.AgendaService;
import br.cesarschool.poo.projeto.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired private AgendaService service;
    @Autowired private ChamadoService chamadoService;
    @Autowired private TecnicoRepository tecnicoRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listar(Model model,
                          @RequestParam(required = false) String filtro,
                          Authentication auth) {

        boolean isAdmin = isAdmin(auth);
        String hoje = LocalDate.now().toString();

        List<Agendamento> lista;
        if ("hoje".equals(filtro)) {
            lista = service.buscarPorData(hoje);
        } else if (filtro != null && !filtro.isBlank()) {
            lista = service.buscarPorStatus(filtro);
        } else {
            lista = service.listarTodos();
        }

        if (!isAdmin) {
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario != null && usuario.getTecnico() != null) {
                String nomeTecnico = usuario.getTecnico().getNome();
                lista = lista.stream()
                        .filter(a -> nomeTecnico.equals(a.getTecnico()))
                        .collect(Collectors.toList());
            }
        }

        model.addAttribute("agendamentos", lista);
        model.addAttribute("filtro", filtro != null ? filtro : "Todos");
        model.addAttribute("hoje", hoje);
        model.addAttribute("isAdmin", isAdmin);
        return "agenda/lista";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model, Authentication auth) {
        Agendamento ag = service.buscarPorId(id).orElseThrow();
        boolean isAdmin = isAdmin(auth);

        boolean isResponsavel = false;
        if (!isAdmin) {
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario != null && usuario.getTecnico() != null) {
                isResponsavel = usuario.getTecnico().getNome().equals(ag.getTecnico());
            }
        }

        model.addAttribute("ag", ag);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isResponsavel", isResponsavel);

        if (ag.getChamadoId() != null) {
            chamadoService.buscarPorId(ag.getChamadoId())
                    .ifPresent(c -> model.addAttribute("chamadoVinculado", c));
        }

        return "agenda/detalhe";
    }

    @GetMapping("/novo")
    public String novoForm(@RequestParam(required = false) Long chamadoId, Model model) {
        Agendamento ag = new Agendamento();
        if (chamadoId != null) {
            chamadoService.buscarPorId(chamadoId).ifPresent(chamado -> {
                ag.setChamadoId(chamadoId);
                ag.setClienteNome(chamado.getClienteNome());
                ag.setEndereco(chamado.getEndereco());
                ag.setTecnico(chamado.getTecnico());
                ag.setTitulo("Visita técnica – " + chamado.getNumero());
            });
            model.addAttribute("chamadoOrigem", chamadoId);
        }
        model.addAttribute("ag", ag);
        model.addAttribute("tecnicos", tecnicoRepository.findAll());
        return "agenda/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Agendamento ag = service.buscarPorId(id).orElseThrow();
        model.addAttribute("ag", ag);
        model.addAttribute("tecnicos", tecnicoRepository.findAll());
        if (ag.getChamadoId() != null) {
            chamadoService.buscarPorId(ag.getChamadoId())
                    .ifPresent(c -> model.addAttribute("chamadoOrigem", ag.getChamadoId()));
        }
        return "agenda/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Agendamento ag, RedirectAttributes ra) {
        boolean isNew = (ag.getId() == null);

        if (ag.getDataCadastro() == null || ag.getDataCadastro().isBlank()) {
            ag.setDataCadastro(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (ag.getStatus() == null || ag.getStatus().isBlank()) {
            ag.setStatus("Agendado");
        }
        service.salvar(ag);

        // Sincronização: novo agendamento de chamado → chamado passa para "Em andamento"
        if (isNew && ag.getChamadoId() != null) {
            chamadoService.buscarPorId(ag.getChamadoId()).ifPresent(chamado -> {
                if ("Aberto".equals(chamado.getStatus())) {
                    chamado.setStatus("Em andamento");
                    chamado.setUltimaAtualizacao(timestamp());
                    chamadoService.salvar(chamado);
                }
            });
            ra.addFlashAttribute("sucesso", "Visita agendada com sucesso.");
            return "redirect:/chamados/" + ag.getChamadoId();
        }

        return "redirect:/agenda";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Agendamento ag = service.buscarPorId(id).orElseThrow();
        Long chamadoId = ag.getChamadoId();
        service.excluir(id);
        if (chamadoId != null) return "redirect:/chamados/" + chamadoId;
        return "redirect:/agenda";
    }

    @PostMapping("/realizar/{id}")
    public String realizar(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        Agendamento ag = service.buscarPorId(id).orElseThrow();

        if (!isAdmin(auth)) {
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario == null || usuario.getTecnico() == null
                    || !usuario.getTecnico().getNome().equals(ag.getTecnico())) {
                ra.addFlashAttribute("erro", "Você não é o técnico responsável por este agendamento.");
                return "redirect:/agenda/" + id;
            }
        }

        ag.setStatus("Realizado");
        service.salvar(ag);
        ra.addFlashAttribute("sucesso", "Agendamento marcado como realizado.");
        return "redirect:/agenda/" + id;
    }

    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes ra) {
        Agendamento ag = service.buscarPorId(id).orElseThrow();
        ag.setStatus("Cancelado");
        service.salvar(ag);

        // Sincronização: se não há outro agendamento ativo, chamado volta para "Aberto"
        if (ag.getChamadoId() != null) {
            if (!service.temAgendamentoAtivo(ag.getChamadoId(), ag.getId())) {
                chamadoService.buscarPorId(ag.getChamadoId()).ifPresent(chamado -> {
                    if ("Em andamento".equals(chamado.getStatus())) {
                        chamado.setStatus("Aberto");
                        chamado.setUltimaAtualizacao(timestamp());
                        chamadoService.salvar(chamado);
                    }
                });
            }
            ra.addFlashAttribute("sucesso", "Agendamento cancelado.");
            return "redirect:/chamados/" + ag.getChamadoId();
        }

        ra.addFlashAttribute("sucesso", "Agendamento cancelado.");
        return "redirect:/agenda/" + id;
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private String timestamp() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " às " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
