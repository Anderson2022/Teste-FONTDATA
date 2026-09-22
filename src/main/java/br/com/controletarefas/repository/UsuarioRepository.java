package br.com.controletarefas.repository;

import br.com.controletarefas.entity.Usuario;
import br.com.controletarefas.enums.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    long countByPerfil(Perfil perfil);
}
