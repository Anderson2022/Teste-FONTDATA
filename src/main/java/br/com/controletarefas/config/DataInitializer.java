package br.com.controletarefas.config;

import br.com.controletarefas.entity.Usuario;
import br.com.controletarefas.enums.Perfil;
import br.com.controletarefas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner inicializarDados(UsuarioRepository usuarios, PasswordEncoder encoder) {
        return args -> {
            criarUsuarioSeAusente(usuarios, encoder, "Administrador", "admin", "admin123", Perfil.SUPERVISOR);
            criarUsuarioSeAusente(usuarios, encoder, "Operador Teste", "operador", "operador123", Perfil.OPERADOR);
        };
    }

    private void criarUsuarioSeAusente(UsuarioRepository repository, PasswordEncoder encoder, String nome,
            String username, String senha, Perfil perfil) {
        if (!repository.existsByUsername(username)) {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setUsername(username);
            usuario.setSenha(encoder.encode(senha));
            usuario.setPerfil(perfil);
            usuario.setAtivo(true);
            repository.save(usuario);
        }
    }
}