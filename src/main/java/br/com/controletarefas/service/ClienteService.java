package br.com.controletarefas.service;

import br.com.controletarefas.entity.Cliente;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.ClienteRepository;
import br.com.controletarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final TarefaRepository tarefas;

    public ClienteService(ClienteRepository repository, TarefaRepository tarefas) {
        this.repository = repository;
        this.tarefas = tarefas;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarAtivos() {
        return repository.findByAtivoTrueOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public Cliente buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new RegraNegocioException("Cliente não encontrado."));
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        cliente.setNome(cliente.getNome().trim());
        boolean duplicado = cliente.getId() == null ? repository.existsByNomeIgnoreCase(cliente.getNome())
                : repository.existsByNomeIgnoreCaseAndIdNot(cliente.getNome(), cliente.getId());
        if (duplicado)
            throw new RegraNegocioException("Já existe um cliente com este nome.");
        return repository.save(cliente);
    }

    @Transactional
    public void alternar(Long id) {
        Cliente cliente = buscar(id);
        if (cliente.isAtivo() && tarefas.existsByClienteId(id))
            throw new RegraNegocioException("O cliente está em uso e não pode ser desativado.");
        cliente.setAtivo(!cliente.isAtivo());
    }
}