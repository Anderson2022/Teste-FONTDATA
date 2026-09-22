package br.com.controletarefas.service;

import br.com.controletarefas.entity.*;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AtribuicaoService {
    private final UsuarioTarefaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final TarefaRepository tarefaRepository;
    public AtribuicaoService(UsuarioTarefaRepository repository, UsuarioRepository usuarioRepository, TarefaRepository tarefaRepository) {
        this.repository = repository; this.usuarioRepository = usuarioRepository; this.tarefaRepository = tarefaRepository;
    }
    @Transactional
    public UsuarioTarefa atribuir(Long usuarioId, Long tarefaId, String usernameSupervisor) {
        if (repository.existsByUsuarioIdAndTarefaId(usuarioId, tarefaId)) throw new RegraNegocioException("Tarefa já atribuída ao usuário.");
        UsuarioTarefa atribuicao = new UsuarioTarefa();
        atribuicao.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow(() -> new RegraNegocioException("Usuário não encontrado.")));
        atribuicao.setTarefa(tarefaRepository.findById(tarefaId).orElseThrow(() -> new RegraNegocioException("Tarefa não encontrada.")));
        atribuicao.setAtribuidoPor(usuarioRepository.findByUsername(usernameSupervisor).orElseThrow(() -> new RegraNegocioException("Supervisor não encontrado.")));
        return repository.save(atribuicao);
    }
    @Transactional(readOnly = true) public List<UsuarioTarefa> buscarPorUsuario(Long usuarioId) { return repository.findByUsuarioIdOrderByTarefaNome(usuarioId); }
    @Transactional(readOnly = true) public List<UsuarioTarefa> buscarDoUsuarioAutenticado(String username) { return repository.findByUsuarioUsernameOrderByTarefaNome(username); }
}
