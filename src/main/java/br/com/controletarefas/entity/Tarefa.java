package br.com.controletarefas.entity;

import br.com.controletarefas.enums.TipoTarefa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 150)

    @Column(nullable = false, length = 150)
    private String nome;

    @NotNull(message = "Tipo é obrigatório.")
    @Enumerated(EnumType.STRING)

    @Column(nullable = false, length = 20)
    private TipoTarefa tipo;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
    
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @PrePersist
    void prePersist() {
        criadoEm = atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }

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

    public TipoTarefa getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarefa tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
