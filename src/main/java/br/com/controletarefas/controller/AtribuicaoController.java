package br.com.controletarefas.controller;

import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.service.AtribuicaoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/atribuicoes")
@PreAuthorize("hasRole('SUPERVISOR')")
public class AtribuicaoController {
    private final AtribuicaoService service;

    public AtribuicaoController(AtribuicaoService service) {
        this.service = service;
    }

    @GetMapping
    public String pagina(@RequestParam(required = false) Long usuarioId, Model model) {
        model.addAttribute("usuarios", service.listarUsuarios());
        model.addAttribute("usuarioId", usuarioId);
        if (usuarioId != null) {
            model.addAttribute("usuarioSelecionado", service.buscarUsuario(usuarioId));
            model.addAttribute("atribuicoes", service.buscarPorUsuario(usuarioId));
            model.addAttribute("tarefasDisponiveis", service.buscarDisponiveis(usuarioId));
        }
        return "atribuicoes/index";
    }

    @PostMapping
    public String atribuir(@RequestParam Long usuarioId, @RequestParam Long tarefaId,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            service.atribuir(usuarioId, tarefaId, authentication.getName());
            redirectAttributes.addFlashAttribute("sucesso", "Tarefa atribuída com sucesso.");
        } catch (RegraNegocioException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
        }
        return "redirect:/atribuicoes?usuarioId=" + usuarioId;
    }

    @PostMapping("/{atribuicaoId}/remover")
    public String remover(@PathVariable Long atribuicaoId, @RequestParam Long usuarioId,
            RedirectAttributes redirectAttributes) {
        try {
            service.remover(atribuicaoId, usuarioId);
            redirectAttributes.addFlashAttribute("sucesso", "Atribuição removida com sucesso.");
        } catch (RegraNegocioException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
        }
        return "redirect:/atribuicoes?usuarioId=" + usuarioId;
    }
}