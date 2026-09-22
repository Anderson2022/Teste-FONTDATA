package br.com.controletarefas.controller;

import br.com.controletarefas.dto.TarefaForm;
import br.com.controletarefas.enums.ClassificacaoTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.service.ClienteService;
import br.com.controletarefas.service.TarefaService;
import br.com.controletarefas.service.TipoTarefaService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tarefas")
@PreAuthorize("hasRole('SUPERVISOR')")
public class TarefaController {
    private final TarefaService tarefas;
    private final TipoTarefaService tipos;
    private final ClienteService clientes;

    public TarefaController(TarefaService tarefas, TipoTarefaService tipos, ClienteService clientes) {
        this.tarefas = tarefas;
        this.tipos = tipos;
        this.clientes = clientes;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tarefas", tarefas.listar());
        return "tarefas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("tarefaForm", new TarefaForm());
        adicionarOpcoes(model);
        return "tarefas/formulario";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute TarefaForm tarefaForm, BindingResult result, Model model,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            adicionarOpcoes(model);
            return "tarefas/formulario";
        }
        try {
            tarefas.salvar(tarefaForm);
            redirect.addFlashAttribute("sucesso", "Tarefa cadastrada com sucesso.");
            return "redirect:/tarefas";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            adicionarOpcoes(model);
            return "tarefas/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tarefaForm", tarefas.criarForm(id));
        adicionarOpcoes(model);
        return "tarefas/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute TarefaForm tarefaForm, BindingResult result,
            Model model, RedirectAttributes redirect) {
        tarefaForm.setId(id);
        if (result.hasErrors()) {
            adicionarOpcoes(model);
            return "tarefas/formulario";
        }
        try {
            tarefas.atualizar(id, tarefaForm);
            redirect.addFlashAttribute("sucesso", "Tarefa atualizada com sucesso.");
            return "redirect:/tarefas";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            adicionarOpcoes(model);
            return "tarefas/formulario";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            tarefas.excluir(id);
            redirect.addFlashAttribute("sucesso", "Tarefa excluída com sucesso.");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tarefas";
    }

    private void adicionarOpcoes(Model model) {
        model.addAttribute("tipos", tipos.listarAtivos());
        model.addAttribute("clientes", clientes.listarAtivos());
        model.addAttribute("classificacoes", ClassificacaoTarefa.values());
    }
}