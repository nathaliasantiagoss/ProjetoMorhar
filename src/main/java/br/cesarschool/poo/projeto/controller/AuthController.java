package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.Usuario;
import br.cesarschool.poo.projeto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/recuperar-senha")
    public String recuperarSenhaForm() {
        return "recuperar-senha";
    }

    @PostMapping("/recuperar-senha")
    public String recuperarSenha(@RequestParam String email, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTecnicoEmail(email);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("erro", "Nenhuma conta encontrada com este e-mail.");
            return "recuperar-senha";
        }
        model.addAttribute("userId", usuarioOpt.get().getId());
        model.addAttribute("email", email);
        return "redefinir-senha";
    }

    @PostMapping("/recuperar-senha/redefinir")
    public String redefinirSenha(@RequestParam Long userId,
                                  @RequestParam String novaSenha,
                                  @RequestParam String confirmarSenha,
                                  RedirectAttributes ra) {
        if (!novaSenha.equals(confirmarSenha)) {
            ra.addFlashAttribute("erro", "As senhas não coincidem.");
            return "redirect:/recuperar-senha";
        }
        if (novaSenha.length() < 6) {
            ra.addFlashAttribute("erro", "A senha deve ter pelo menos 6 caracteres.");
            return "redirect:/recuperar-senha";
        }
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        ra.addFlashAttribute("sucesso", "Senha redefinida com sucesso! Faça o login.");
        return "redirect:/login";
    }
}
