package br.com.controletarefas.controller;

import br.com.controletarefas.dto.UsuarioForm;
import br.com.controletarefas.enums.Perfil;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('SUPERVISOR')")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listar());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuarioForm", new UsuarioForm());
        adicionarPerfis(model);
        return "usuarios/formulario";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute UsuarioForm usuarioForm, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        if (usuarioForm.getSenha() == null || usuarioForm.getSenha().isBlank())
            result.rejectValue("senha", "obrigatoria", "Senha é obrigatória.");
        if (result.hasErrors()) {
            adicionarPerfis(model);
            return "usuarios/formulario";
        }
        try {
            service.cadastrar(usuarioForm);
        } catch (RegraNegocioException exception) {
            result.rejectValue(exception.getMessage().startsWith("Usuário já") ? "username" : "senha", "regra",
                    exception.getMessage());
            adicionarPerfis(model);
            return "usuarios/formulario";
        }
        redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso.");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuarioForm", service.buscarFormulario(id));
        adicionarPerfis(model);
        return "usuarios/formulario";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute UsuarioForm usuarioForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        usuarioForm.setId(id);
        if (result.hasErrors()) {
            adicionarPerfis(model);
            return "usuarios/formulario";
        }
        try {
            service.atualizar(id, usuarioForm);
        } catch (RegraNegocioException exception) {
            result.rejectValue("username", "regra", exception.getMessage());
            adicionarPerfis(model);
            return "usuarios/formulario";
        }
        redirectAttributes.addFlashAttribute("sucesso", "Usuário atualizado com sucesso.");
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/alternar-ativo")
    public String alternarAtivo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.alternarAtivo(id);
        redirectAttributes.addFlashAttribute("sucesso", "Status do usuário atualizado.");
        return "redirect:/usuarios";
    }

    private void adicionarPerfis(Model model) {
        model.addAttribute("perfis", Perfil.values());
    }
}