package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.service.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalAbertos", chamadoService.contarAbertos());
        model.addAttribute("totalEmAndamento", chamadoService.contarEmAndamento());
        model.addAttribute("totalFinalizados", chamadoService.contarFinalizados());
        model.addAttribute("ultimosChamados",
            chamadoService.listarTodos().stream().limit(3).collect(Collectors.toList()));
        return "dashboard";
    }
}
