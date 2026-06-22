package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.Usuario;
import br.cesarschool.poo.projeto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PerfilController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "perfil/index";
    }

    @PostMapping("/perfil/senha")
    public String trocarSenha(@RequestParam String senhaAtual,
                               @RequestParam String novaSenha,
                               @RequestParam String confirmarSenha,
                               Authentication auth,
                               RedirectAttributes ra) {
        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElseThrow();

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            ra.addFlashAttribute("erro", "Senha atual incorreta.");
            return "redirect:/perfil";
        }
        if (!novaSenha.equals(confirmarSenha)) {
            ra.addFlashAttribute("erro", "A nova senha e a confirmação não coincidem.");
            return "redirect:/perfil";
        }
        if (novaSenha.length() < 6) {
            ra.addFlashAttribute("erro", "A nova senha deve ter pelo menos 6 caracteres.");
            return "redirect:/perfil";
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        ra.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
        return "redirect:/perfil";
    }
}
