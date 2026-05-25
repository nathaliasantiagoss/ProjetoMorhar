package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.*;
import br.cesarschool.poo.projeto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String nome) {
        List<Cliente> clientes = (nome != null && !nome.isBlank())
                ? service.buscarPorNome(nome)
                : service.listarTodos();
        model.addAttribute("clientes", clientes);
        model.addAttribute("nomeBusca", nome);
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        ClienteForm form = new ClienteForm();
        form.setDataCadastro(LocalDate.now().toString());
        form.setPerfil("Pessoa Física");
        form.setStatus("Ativo");
        model.addAttribute("form", form);
        return "clientes/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Cliente cliente = service.buscarPorId(id).orElseThrow();
        model.addAttribute("form", ClienteForm.fromCliente(cliente));
        return "clientes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ClienteForm form) {
        boolean isEdicao = form.getId() != null;
        Cliente cliente;

        if ("Pessoa Física".equals(form.getPerfil())) {
            ClientePessoaFisica pf = isEdicao
                    ? (ClientePessoaFisica) service.buscarPorId(form.getId()).orElseThrow()
                    : new ClientePessoaFisica();
            pf.setNome(form.getNome());
            pf.setEmail(form.getEmail());
            pf.setTelefone(form.getTelefone());
            pf.setStatus(form.getStatus());
            pf.setCpf(form.getDocumento());
            if (!isEdicao) pf.setDataCadastro(form.getDataCadastro());
            cliente = pf;
        } else {
            ClientePessoaJuridica pj = isEdicao
                    ? (ClientePessoaJuridica) service.buscarPorId(form.getId()).orElseThrow()
                    : new ClientePessoaJuridica();
            pj.setNome(form.getNome());
            pj.setEmail(form.getEmail());
            pj.setTelefone(form.getTelefone());
            pj.setStatus(form.getStatus());
            pj.setCnpj(form.getDocumento());
            if (!isEdicao) pj.setDataCadastro(form.getDataCadastro());
            cliente = pj;
        }

        service.salvar(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/clientes";
    }
}
