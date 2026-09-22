package br.com.controletarefas.repository;

import br.com.controletarefas.entity.UsuarioTarefa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioTarefaRepository extends JpaRepository<UsuarioTarefa, Long> {
    boolean existsByUsuarioIdAndTarefaId(Long usuarioId, Long tarefaId);

    @EntityGraph(attributePaths = {"tarefa"})
    List<UsuarioTarefa> findByUsuarioIdOrderByTarefaNome(Long usuarioId);

    @EntityGraph(attributePaths = {"tarefa"})
    List<UsuarioTarefa> findByUsuarioUsernameOrderByTarefaNome(String username);

    boolean existsByTarefaId(Long tarefaId);

    void deleteByIdAndUsuarioId(Long id, Long usuarioId);
}