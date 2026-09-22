package br.com.controletarefas.service;

import br.com.controletarefas.dto.UsuarioForm;
import br.com.controletarefas.entity.Usuario;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));
    }

    @Transactional(readOnly = true)
    public UsuarioForm buscarFormulario(Long id) {
        Usuario usuario = buscarPorId(id);
        UsuarioForm form = new UsuarioForm();
        form.setId(usuario.getId());
        form.setNome(usuario.getNome());
        form.setUsername(usuario.getUsername());
        form.setPerfil(usuario.getPerfil());
        form.setAtivo(usuario.isAtivo());
        return form;
    }

    @Transactional
    public Usuario cadastrar(UsuarioForm form) {
        validarSenhaNova(form.getSenha());
        if (repository.existsByUsername(form.getUsername()))
            throw new RegraNegocioException("Usuário já cadastrado.");
        Usuario usuario = new Usuario();
        copiarCampos(form, usuario);
        usuario.setSenha(passwordEncoder.encode(form.getSenha()));
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioForm form) {
        if (repository.existsByUsernameAndIdNot(form.getUsername(), id))
            throw new RegraNegocioException("Usuário já cadastrado.");
        Usuario usuario = buscarPorId(id);
        copiarCampos(form, usuario);
        if (form.getSenha() != null && !form.getSenha().isBlank())
            usuario.setSenha(passwordEncoder.encode(form.getSenha()));
        return repository.save(usuario);
    }

    @Transactional
    public void alternarAtivo(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(!usuario.isAtivo());
        repository.save(usuario);
    }

    private void validarSenhaNova(String senha) {
        if (senha == null || senha.isBlank())
            throw new RegraNegocioException("Senha é obrigatória.");
        if (senha.length() < 6)
            throw new RegraNegocioException("Senha deve ter pelo menos 6 caracteres.");
    }

    private void copiarCampos(UsuarioForm form, Usuario usuario) {
        usuario.setNome(form.getNome().trim());
        usuario.setUsername(form.getUsername().trim());
        usuario.setPerfil(form.getPerfil());
        usuario.setAtivo(form.isAtivo());
    }
}