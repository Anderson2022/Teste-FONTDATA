package br.com.controletarefas.service;

import br.com.controletarefas.entity.TipoTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.TarefaRepository;
import br.com.controletarefas.repository.TipoTarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TipoTarefaService {
    private final TipoTarefaRepository repository;
    private final TarefaRepository tarefas;

    public TipoTarefaService(TipoTarefaRepository repository, TarefaRepository tarefas) {
        this.repository = repository;
        this.tarefas = tarefas;
    }

    @Transactional(readOnly = true)
    public List<TipoTarefa> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<TipoTarefa> listarAtivos() {
        return repository.findByAtivoTrueOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public TipoTarefa buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RegraNegocioException("Tipo não encontrado."));
    }

    @Transactional
    public TipoTarefa salvar(TipoTarefa tipo) {
        tipo.setNome(tipo.getNome().trim());
        boolean duplicado = tipo.getId() == null ? repository.existsByNomeIgnoreCase(tipo.getNome())
                : repository.existsByNomeIgnoreCaseAndIdNot(tipo.getNome(), tipo.getId());
        if (duplicado)
            throw new RegraNegocioException("Já existe um tipo com este nome.");
        return repository.save(tipo);
    }

    @Transactional
    public void alternar(Long id) {
        TipoTarefa tipo = buscar(id);
        if (tipo.isAtivo() && tarefas.existsByTipoId(id))
            throw new RegraNegocioException("O tipo está em uso e não pode ser desativado.");
        tipo.setAtivo(!tipo.isAtivo());
    }
}