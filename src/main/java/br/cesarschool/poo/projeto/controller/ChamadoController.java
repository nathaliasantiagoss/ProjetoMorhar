package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.Chamado;
import br.cesarschool.poo.projeto.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService service;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String status) {
        List<Chamado> chamados = (status != null && !status.isBlank())
                ? service.buscarPorStatus(status)
                : service.listarTodos();
        model.addAttribute("chamados", chamados);
        model.addAttribute("statusFiltro", status != null ? status : "Todos");
        return "chamados/lista";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();
        model.addAttribute("chamado", chamado);
        return "chamados/detalhe";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("chamado", new Chamado());
        return "chamados/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();
        model.addAttribute("chamado", chamado);
        return "chamados/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Chamado chamado) {
        if (chamado.getNumero() == null || chamado.getNumero().isBlank()) {
            String ano = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
            long seq = service.listarTodos().size() + 1;
            chamado.setNumero(String.format("CH-%s-%04d", ano, seq));
        }
        if (chamado.getDataAbertura() == null || chamado.getDataAbertura().isBlank()) {
            chamado.setDataAbertura(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (chamado.getHoraAbertura() == null || chamado.getHoraAbertura().isBlank()) {
            chamado.setHoraAbertura(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        chamado.setUltimaAtualizacao(chamado.getDataAbertura() + " às " + chamado.getHoraAbertura());
        service.salvar(chamado);
        return "redirect:/chamados";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/chamados";
    }

    @PostMapping("/finalizar/{id}")
    public String finalizar(@PathVariable Long id) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();
        chamado.setStatus("Finalizado");
        chamado.setUltimaAtualizacao(
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " às " +
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        service.salvar(chamado);
        return "redirect:/chamados/" + id;
    }
}
