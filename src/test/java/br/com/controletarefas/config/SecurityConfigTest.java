package br.com.controletarefas.config;

import br.com.controletarefas.controller.AuthController;
import br.com.controletarefas.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class SecurityConfigTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UsuarioRepository usuarioRepository;

    @Test
    @WithMockUser(username = "operador", roles = "OPERADOR")
    void operadorNaoPodeAcessarAtribuicoes() throws Exception {
        mockMvc.perform(get("/atribuicoes")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "operador", roles = "OPERADOR")
    void operadorNaoPodeAcessarUsuarios() throws Exception {
        mockMvc.perform(get("/usuarios")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "operador", roles = "OPERADOR")
    void operadorNaoPodeExcluirTarefa() throws Exception {
        mockMvc.perform(post("/tarefas/1/excluir").with(csrf()))
                .andExpect(status().isForbidden());
    }
}