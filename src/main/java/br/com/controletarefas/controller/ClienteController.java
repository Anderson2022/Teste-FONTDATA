package br.com.controletarefas.controller;

import br.com.controletarefas.entity.Cliente;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
@PreAuthorize("hasRole('SUPERVISOR')")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listar());
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscar(id));
        return "clientes/formulario";
    }

    @PostMapping({ "", "/{id}" })
    public String salvar(@PathVariable(required = false) Long id, @Valid @ModelAttribute Cliente cliente,
            BindingResult result, Model model, RedirectAttributes redirect) {
        if (id != null)
            cliente.setId(id);
        if (result.hasErrors())
            return "clientes/formulario";
        try {
            service.salvar(cliente);
            redirect.addFlashAttribute("sucesso", "Cliente salvo com sucesso.");
            return "redirect:/clientes";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "clientes/formulario";
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
        return "redirect:/clientes";
    }
}