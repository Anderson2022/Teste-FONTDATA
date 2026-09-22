package br.com.controletarefas.service;

import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.TarefaRepository;
import br.com.controletarefas.repository.UsuarioTarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioTarefaRepository usuarioTarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioTarefaRepository usuarioTarefaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioTarefaRepository = usuarioTarefaRepository;
    }

    @Transactional
    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizar(Long id, Tarefa dados) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setNome(dados.getNome());
        tarefa.setTipo(dados.getTipo());
        return tarefaRepository.save(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listar() {
        return tarefaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Tarefa não encontrada."));
    }

    @Transactional
    public void excluir(Long id) {
        if (usuarioTarefaRepository.existsByTarefaId(id)) {
            throw new RegraNegocioException("Não é possível excluir esta tarefa porque existem usuários vinculados.");
        }
        if (!tarefaRepository.existsById(id)) {
            throw new RegraNegocioException("Tarefa não encontrada.");
        }
        tarefaRepository.deleteById(id);
    }
}
