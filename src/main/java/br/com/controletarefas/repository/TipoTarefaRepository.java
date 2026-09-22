package br.com.controletarefas.repository;

import br.com.controletarefas.entity.TipoTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TipoTarefaRepository extends JpaRepository<TipoTarefa, Long> {
    List<TipoTarefa> findAllByOrderByNomeAsc();

    List<TipoTarefa> findByAtivoTrueOrderByNomeAsc();

    Optional<TipoTarefa> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}