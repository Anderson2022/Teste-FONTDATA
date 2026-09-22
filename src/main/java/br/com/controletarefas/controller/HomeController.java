package br.com.controletarefas.controller;

import br.com.controletarefas.service.AtribuicaoService;
import br.com.controletarefas.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final DashboardService dashboardService; private final AtribuicaoService atribuicaoService;
    public HomeController(DashboardService dashboardService, AtribuicaoService atribuicaoService) {
        this.dashboardService = dashboardService; this.atribuicaoService = atribuicaoService;
    }
    @GetMapping("/") String inicio(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR")) ? "redirect:/dashboard" : "redirect:/minhas-tarefas";
    }
    @GetMapping("/dashboard") String dashboard(Model model) { model.addAttribute("resumo", dashboardService.resumo()); return "dashboard"; }
    @GetMapping("/minhas-tarefas") String minhasTarefas(Authentication auth, Model model) {
        model.addAttribute("atribuicoes", atribuicaoService.buscarDoUsuarioAutenticado(auth.getName())); return "minhas-tarefas";
    }
}
