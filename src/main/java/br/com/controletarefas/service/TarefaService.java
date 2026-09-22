package br.com.controletarefas.service;

import br.com.controletarefas.dto.TarefaForm;
import br.com.controletarefas.entity.Cliente;
import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.entity.TipoTarefa;
import br.com.controletarefas.enums.ClassificacaoTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioTarefaRepository usuarioTarefaRepository;
    private final TipoTarefaRepository tipoRepository;
    private final ClienteRepository clienteRepository;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioTarefaRepository usuarioTarefaRepository,
            TipoTarefaRepository tipoRepository, ClienteRepository clienteRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioTarefaRepository = usuarioTarefaRepository;
        this.tipoRepository = tipoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Tarefa salvar(TarefaForm form) {
        return tarefaRepository.save(aplicar(new Tarefa(), form));
    }

    @Transactional
    public Tarefa atualizar(Long id, TarefaForm form) {
        return tarefaRepository.save(aplicar(buscarPorId(id), form));
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listar() {
        return tarefaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id).orElseThrow(() -> new RegraNegocioException("Tarefa não encontrada."));
    }

    @Transactional(readOnly = true)
    public TarefaForm criarForm(Long id) {
        Tarefa tarefa = buscarPorId(id);
        TarefaForm form = new TarefaForm();
        form.setId(tarefa.getId());
        form.setNome(tarefa.getNome());
        form.setDescricao(tarefa.getDescricao());
        form.setTipoId(tarefa.getTipo().getId());
        form.setClassificacao(tarefa.getClassificacao());
        form.setClienteId(tarefa.getCliente() == null ? null : tarefa.getCliente().getId());
        return form;
    }

    @Transactional
    public void excluir(Long id) {
        if (usuarioTarefaRepository.existsByTarefaId(id))
            throw new RegraNegocioException("Não é possível excluir esta tarefa porque existem usuários vinculados.");
        if (!tarefaRepository.existsById(id))
            throw new RegraNegocioException("Tarefa não encontrada.");
        tarefaRepository.deleteById(id);
    }

    private Tarefa aplicar(Tarefa tarefa, TarefaForm form) {
        TipoTarefa tipo = tipoRepository.findById(form.getTipoId())
                .orElseThrow(() -> new RegraNegocioException("Tipo de tarefa não encontrado."));
        if (!tipo.isAtivo() && (tarefa.getId() == null || tarefa.getTipo() == null
                || !tipo.getId().equals(tarefa.getTipo().getId())))
            throw new RegraNegocioException("Selecione um tipo de tarefa ativo.");
        Cliente cliente = null;
        if (form.getClassificacao() == ClassificacaoTarefa.CLIENTE) {
            if (form.getClienteId() == null)
                throw new RegraNegocioException("Selecione o cliente da tarefa.");
            cliente = clienteRepository.findById(form.getClienteId())
                    .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado."));
            if (!cliente.isAtivo()
                    && (tarefa.getCliente() == null || !cliente.getId().equals(tarefa.getCliente().getId())))
                throw new RegraNegocioException("Selecione um cliente ativo.");
        }
        tarefa.setNome(form.getNome().trim());
        tarefa.setDescricao(form.getDescricao().trim());
        tarefa.setTipo(tipo);
        tarefa.setClassificacao(form.getClassificacao());
        tarefa.setCliente(cliente);
        return tarefa;
    }
}