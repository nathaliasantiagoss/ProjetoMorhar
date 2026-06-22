package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.Tecnico;
import br.cesarschool.poo.projeto.model.TecnicoForm;
import br.cesarschool.poo.projeto.model.Usuario;
import br.cesarschool.poo.projeto.repository.UsuarioRepository;
import br.cesarschool.poo.projeto.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tecnicos")
public class TecnicoController {

    @Autowired private TecnicoService service;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String nome) {
        List<Tecnico> tecnicos = (nome != null && !nome.isBlank())
                ? service.buscarPorNome(nome)
                : service.listarTodos();
        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("nomeBusca", nome);
        return "tecnicos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        TecnicoForm form = new TecnicoForm();
        form.setDataCadastro(LocalDate.now().toString());
        form.setStatus("Ativo");
        model.addAttribute("form", form);
        return "tecnicos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Tecnico tecnico = service.buscarPorId(id).orElseThrow();
        model.addAttribute("form", TecnicoForm.fromTecnico(tecnico));
        return "tecnicos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TecnicoForm form, RedirectAttributes ra) {
        boolean isEdicao = form.getId() != null;
        Tecnico tecnico = isEdicao
                ? service.buscarPorId(form.getId()).orElseThrow()
                : new Tecnico();

        tecnico.setNome(form.getNome());
        tecnico.setCpf(form.getCpf());
        tecnico.setEmail(form.getEmail());
        tecnico.setTelefone(form.getTelefone());
        tecnico.setEspecialidade(form.getEspecialidade());
        tecnico.setStatus(form.getStatus());
        if (!isEdicao) tecnico.setDataCadastro(form.getDataCadastro());

        service.salvar(tecnico);

        if (!isEdicao) {
            String username = form.getNome().trim().toLowerCase().replace(" ", ".");
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setSenha(passwordEncoder.encode(form.getSenhaCadastro()));
            usuario.setPerfil("TECNICO");
            usuario.setTecnico(tecnico);
            usuarioRepository.save(usuario);
            ra.addFlashAttribute("sucesso", "Técnico cadastrado. Login: " + username);
        }

        return "redirect:/tecnicos";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/tecnicos";
    }
}
