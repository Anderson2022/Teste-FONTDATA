package br.com.controletarefas.repository;

import br.com.controletarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    boolean existsByNome(String nome);
}
