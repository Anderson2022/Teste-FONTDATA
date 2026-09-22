package br.com.controletarefas.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_tarefa", uniqueConstraints = @UniqueConstraint(name = "uk_usuario_tarefa", columnNames = {"usuario_id", "tarefa_id"}))
public class UsuarioTarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa;
    @Column(name = "atribuido_em", nullable = false, updatable = false)
    private LocalDateTime atribuidoEm;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "atribuido_por", nullable = false)
    private Usuario atribuidoPor;

    @PrePersist void prePersist() { atribuidoEm = LocalDateTime.now(); }
    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Tarefa getTarefa() { return tarefa; }
    public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
    public LocalDateTime getAtribuidoEm() { return atribuidoEm; }
    public Usuario getAtribuidoPor() { return atribuidoPor; }
    public void setAtribuidoPor(Usuario atribuidoPor) { this.atribuidoPor = atribuidoPor; }
}
