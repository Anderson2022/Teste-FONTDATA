package br.com.controletarefas.service;

import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.entity.Usuario;
import br.com.controletarefas.entity.UsuarioTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.TarefaRepository;
import br.com.controletarefas.repository.UsuarioRepository;
import br.com.controletarefas.repository.UsuarioTarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AtribuicaoService {
    private final UsuarioTarefaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TarefaRepository tarefaRepository;

    public AtribuicaoService(UsuarioTarefaRepository repository, UsuarioRepository usuarioRepository, TarefaRepository tarefaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional
    public UsuarioTarefa atribuir(Long usuarioId, Long tarefaId, String usernameSupervisor) {
        if (repository.existsByUsuarioIdAndTarefaId(usuarioId, tarefaId)) throw new RegraNegocioException("Tarefa já atribuída ao usuário.");
        UsuarioTarefa atribuicao = new UsuarioTarefa();
        atribuicao.setUsuario(buscarUsuario(usuarioId));
        atribuicao.setTarefa(tarefaRepository.findById(tarefaId).orElseThrow(() -> new RegraNegocioException("Tarefa não encontrada.")));
        atribuicao.setAtribuidoPor(usuarioRepository.findByUsername(usernameSupervisor).orElseThrow(() -> new RegraNegocioException("Supervisor não encontrado.")));
        return repository.save(atribuicao);
    }

    @Transactional
    public void remover(Long atribuicaoId, Long usuarioId) {
        UsuarioTarefa atribuicao = repository.findById(atribuicaoId).orElseThrow(() -> new RegraNegocioException("Atribuição não encontrada."));
        if (!atribuicao.getUsuario().getId().equals(usuarioId)) throw new RegraNegocioException("Atribuição não pertence ao usuário selecionado.");
        repository.delete(atribuicao);
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAllByOrderByNomeAsc(); }

    @Transactional(readOnly = true)
    public List<UsuarioTarefa> buscarPorUsuario(Long usuarioId) { return repository.findByUsuarioIdOrderByTarefaNome(usuarioId); }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarDisponiveis(Long usuarioId) {
        Set<Long> atribuidas = buscarPorUsuario(usuarioId).stream().map(item -> item.getTarefa().getId()).collect(Collectors.toSet());
        return tarefaRepository.findAll().stream().filter(tarefa -> !atribuidas.contains(tarefa.getId())).toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTarefa> buscarDoUsuarioAutenticado(String username) { return repository.findByUsuarioUsernameOrderByTarefaNome(username); }
}