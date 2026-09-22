package br.com.controletarefas.controller;

import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.enums.TipoTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tarefas")
@PreAuthorize("hasRole('SUPERVISOR')")
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tarefas", tarefaService.listar());
        return "tarefas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        adicionarTipos(model);
        return "tarefas/formulario";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("tarefa") Tarefa tarefa,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            adicionarTipos(model);
            return "tarefas/formulario";
        }
        tarefaService.salvar(tarefa);
        redirectAttributes.addFlashAttribute("sucesso", "Tarefa cadastrada com sucesso.");
        return "redirect:/tarefas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tarefa", tarefaService.buscarPorId(id));
        adicionarTipos(model);
        return "tarefas/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
            @Valid @ModelAttribute("tarefa") Tarefa tarefa,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            tarefa.setId(id);
            adicionarTipos(model);
            return "tarefas/formulario";
        }
        tarefaService.atualizar(id, tarefa);
        redirectAttributes.addFlashAttribute("sucesso", "Tarefa atualizada com sucesso.");
        return "redirect:/tarefas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tarefaService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Tarefa excluída com sucesso.");
        } catch (RegraNegocioException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
        }
        return "redirect:/tarefas";
    }

    private void adicionarTipos(Model model) {
        model.addAttribute("tipos", TipoTarefa.values());
    }
}
