package br.com.controletarefas.entity;

import br.com.controletarefas.enums.Perfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório.") @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nome;
    @NotBlank(message = "Usuário é obrigatório.") @Size(min = 3, max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String username;
    @Column(nullable = false, length = 100)
    private String senha;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private Perfil perfil;
    @Column(nullable = false)
    private boolean ativo = true;
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist void prePersist() { criadoEm = atualizadoEm = LocalDateTime.now(); }
    @PreUpdate void preUpdate() { atualizadoEm = LocalDateTime.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}
