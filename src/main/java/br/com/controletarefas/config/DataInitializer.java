package br.com.controletarefas.config;

import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.entity.Usuario;
import br.com.controletarefas.enums.Perfil;
import br.com.controletarefas.enums.TipoTarefa;
import br.com.controletarefas.repository.TarefaRepository;
import br.com.controletarefas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner inicializarDados(UsuarioRepository usuarios, TarefaRepository tarefas, PasswordEncoder encoder) {
        return args -> {
            criarUsuarioSeAusente(usuarios, encoder, "Administrador", "admin", "admin123", Perfil.SUPERVISOR);
            criarUsuarioSeAusente(usuarios, encoder, "Operador Teste", "operador", "operador123", Perfil.OPERADOR);
            criarTarefaSeAusente(tarefas, "Conferir estoque", TipoTarefa.DIARIA);
            criarTarefaSeAusente(tarefas, "Gerar relatório semanal", TipoTarefa.SEMANAL);
            criarTarefaSeAusente(tarefas, "Conferência quinzenal", TipoTarefa.QUINZENAL);
            criarTarefaSeAusente(tarefas, "Fechamento mensal", TipoTarefa.MENSAL);
        };
    }
    private void criarUsuarioSeAusente(UsuarioRepository repository, PasswordEncoder encoder, String nome, String username, String senha, Perfil perfil) {
        if (!repository.existsByUsername(username)) {
            Usuario usuario = new Usuario(); usuario.setNome(nome); usuario.setUsername(username);
            usuario.setSenha(encoder.encode(senha)); usuario.setPerfil(perfil); usuario.setAtivo(true); repository.save(usuario);
        }
    }
    private void criarTarefaSeAusente(TarefaRepository repository, String nome, TipoTarefa tipo) {
        if (!repository.existsByNome(nome)) { Tarefa tarefa = new Tarefa(); tarefa.setNome(nome); tarefa.setTipo(tipo); repository.save(tarefa); }
    }
}
