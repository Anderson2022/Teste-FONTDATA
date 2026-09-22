package br.com.controletarefas.controller;

import br.com.controletarefas.entity.TipoTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.service.TipoTarefaService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-tarefa")
@PreAuthorize("hasRole('SUPERVISOR')")
public class TipoTarefaController {
    private final TipoTarefaService service;

    public TipoTarefaController(TipoTarefaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tipos", service.listar());
        return "tipos-tarefa/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tipoTarefa", new TipoTarefa());
        return "tipos-tarefa/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tipoTarefa", service.buscar(id));
        return "tipos-tarefa/formulario";
    }

    @PostMapping({ "", "/{id}" })
    public String salvar(@PathVariable(required = false) Long id, @Valid @ModelAttribute TipoTarefa tipoTarefa,
            BindingResult result, Model model, RedirectAttributes redirect) {
        if (id != null)
            tipoTarefa.setId(id);
        if (result.hasErrors())
            return "tipos-tarefa/formulario";
        try {
            service.salvar(tipoTarefa);
            redirect.addFlashAttribute("sucesso", "Tipo salvo com sucesso.");
            return "redirect:/tipos-tarefa";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "tipos-tarefa/formulario";
        }
    }

    @PostMapping("/{id}/alternar-ativo")
    public String alternar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            service.alternar(id);
            redirect.addFlashAttribute("sucesso", "Status atualizado com sucesso.");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tipos-tarefa";
    }
}