package br.com.controletarefas.dto;

import br.com.controletarefas.enums.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioForm {
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres.")
    private String nome;

    @NotBlank(message = "Usuário é obrigatório.")
    @Size(min = 3, max = 60, message = "Usuário deve ter entre 3 e 60 caracteres.")
    private String username;

    @Size(max = 72, message = "Senha deve ter no máximo 72 caracteres.")
    private String senha;

    @NotNull(message = "Perfil é obrigatório.")
    private Perfil perfil;

    private boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}