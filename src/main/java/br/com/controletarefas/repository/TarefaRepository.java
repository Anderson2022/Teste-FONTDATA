package br.com.controletarefas.repository;

import br.com.controletarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    boolean existsByNome(String nome);

    boolean existsByTipoId(Long tipoId);

    boolean existsByClienteId(Long clienteId);

    @Override
    @EntityGraph(attributePaths = { "tipo", "cliente" })
    List<Tarefa> findAll();

    @Override
    @EntityGraph(attributePaths = { "tipo", "cliente" })
    Optional<Tarefa> findById(Long id);
}