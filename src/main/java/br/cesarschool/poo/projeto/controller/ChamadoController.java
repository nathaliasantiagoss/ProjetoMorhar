package br.cesarschool.poo.projeto.controller;

import br.cesarschool.poo.projeto.model.Anexo;
import br.cesarschool.poo.projeto.model.Chamado;
import br.cesarschool.poo.projeto.model.Usuario;
import br.cesarschool.poo.projeto.repository.AnexoRepository;
import br.cesarschool.poo.projeto.repository.UsuarioRepository;
import br.cesarschool.poo.projeto.service.AgendaService;
import br.cesarschool.poo.projeto.service.ChamadoService;
import br.cesarschool.poo.projeto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired private ChamadoService service;
    @Autowired private AgendaService agendaService;
    @Autowired private ClienteService clienteService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private AnexoRepository anexoRepository;

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
    public String detalhe(@PathVariable Long id, Model model, Authentication auth) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();
        model.addAttribute("chamado", chamado);
        model.addAttribute("anexos", anexoRepository.findByChamadoId(id));
        model.addAttribute("agendamentos", agendaService.buscarPorChamado(id));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        boolean responsavel = false;
        if (!isAdmin) {
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario != null && usuario.getTecnico() != null) {
                responsavel = usuario.getTecnico().getNome().equals(chamado.getTecnico());
            }
        }
        model.addAttribute("isResponsavelTecnico", responsavel);

        return "chamados/detalhe";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("chamado", new Chamado());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "chamados/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();
        model.addAttribute("chamado", chamado);
        model.addAttribute("clientes", clienteService.listarTodos());
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

    @PostMapping("/iniciar/{id}")
    public String iniciar(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();

        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null || usuario.getTecnico() == null
                || !usuario.getTecnico().getNome().equals(chamado.getTecnico())) {
            ra.addFlashAttribute("erro", "Você não é o técnico responsável por este chamado.");
            return "redirect:/chamados/" + id;
        }

        if (!"Aberto".equals(chamado.getStatus())) {
            ra.addFlashAttribute("erro", "Este chamado não está com status Aberto.");
            return "redirect:/chamados/" + id;
        }

        chamado.setStatus("Em andamento");
        chamado.setUltimaAtualizacao(
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " às " +
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        service.salvar(chamado);
        ra.addFlashAttribute("sucesso", "Atendimento iniciado com sucesso.");
        return "redirect:/chamados/" + id;
    }

    @PostMapping("/finalizar/{id}")
    public String finalizar(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();

        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null || usuario.getTecnico() == null
                || !usuario.getTecnico().getNome().equals(chamado.getTecnico())) {
            ra.addFlashAttribute("erro", "Você não é o técnico responsável por este chamado.");
            return "redirect:/chamados/" + id;
        }

        chamado.setStatus("Finalizado");
        chamado.setUltimaAtualizacao(
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " às " +
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        service.salvar(chamado);
        return "redirect:/chamados/" + id;
    }

    @GetMapping("/{chamadoId}/anexo/{anexoId}/download")
    public ResponseEntity<Resource> downloadAnexo(@PathVariable Long chamadoId,
                                                   @PathVariable Long anexoId) throws IOException {
        Anexo anexo = anexoRepository.findById(anexoId).orElseThrow();
        Path filePath = Paths.get("uploads/chamados/" + chamadoId).resolve(anexo.getNomeArquivo()).normalize();
        Resource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";
        String disposition = (contentType.startsWith("image/") || contentType.equals("application/pdf"))
                ? "inline" : "attachment";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition + "; filename=\"" + anexo.getNomeArquivo() + "\"")
                .body(resource);
    }

    @PostMapping("/{chamadoId}/anexo/{anexoId}/excluir")
    public String excluirAnexo(@PathVariable Long chamadoId,
                                @PathVariable Long anexoId,
                                Authentication auth,
                                RedirectAttributes ra) {
        Anexo anexo = anexoRepository.findById(anexoId).orElseThrow();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            Chamado chamado = service.buscarPorId(chamadoId).orElseThrow();
            Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuario == null || usuario.getTecnico() == null
                    || !usuario.getTecnico().getNome().equals(chamado.getTecnico())) {
                ra.addFlashAttribute("erro", "Sem permissão para excluir este anexo.");
                return "redirect:/chamados/" + chamadoId;
            }
        }

        Path filePath = Paths.get("uploads/chamados/" + chamadoId + "/" + anexo.getNomeArquivo());
        try { Files.deleteIfExists(filePath); } catch (IOException ignored) {}
        anexoRepository.deleteById(anexoId);
        ra.addFlashAttribute("sucesso", "Anexo excluído com sucesso.");
        return "redirect:/chamados/" + chamadoId;
    }

    @PostMapping("/{id}/anexo")
    public String adicionarAnexo(@PathVariable Long id,
                                  @RequestParam("arquivo") MultipartFile arquivo,
                                  Authentication auth,
                                  RedirectAttributes ra) {
        Chamado chamado = service.buscarPorId(id).orElseThrow();

        Usuario usuario = usuarioRepository.findByUsername(auth.getName()).orElse(null);
        if (usuario == null || usuario.getTecnico() == null
                || !usuario.getTecnico().getNome().equals(chamado.getTecnico())) {
            ra.addFlashAttribute("erro", "Você não é o técnico responsável por este chamado.");
            return "redirect:/chamados/" + id;
        }

        if (arquivo.isEmpty()) {
            ra.addFlashAttribute("erro", "Nenhum arquivo selecionado.");
            return "redirect:/chamados/" + id;
        }

        try {
            String nomeArquivo = arquivo.getOriginalFilename();
            Path uploadDir = Paths.get("uploads/chamados/" + id);
            Files.createDirectories(uploadDir);
            Files.copy(arquivo.getInputStream(), uploadDir.resolve(nomeArquivo), StandardCopyOption.REPLACE_EXISTING);

            Anexo anexo = new Anexo();
            anexo.setChamadoId(id);
            anexo.setNomeArquivo(nomeArquivo);
            anexo.setDataUpload(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            anexoRepository.save(anexo);

            ra.addFlashAttribute("sucesso", "Arquivo anexado com sucesso.");
        } catch (IOException e) {
            ra.addFlashAttribute("erro", "Erro ao salvar o arquivo.");
        }

        return "redirect:/chamados/" + id;
    }
}
