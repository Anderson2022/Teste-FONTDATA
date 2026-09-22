package br.com.controletarefas.entity;

import br.com.controletarefas.enums.ClassificacaoTarefa;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 150) private String nome;
    @Column(nullable = false, length = 500) private String descricao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_id", nullable = false) private TipoTarefa tipo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20) private ClassificacaoTarefa classificacao = ClassificacaoTarefa.INTERNA;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id") private Cliente cliente;
    @Column(name = "criado_em", nullable = false, updatable = false) private LocalDateTime criadoEm;
    @Column(name = "atualizado_em", nullable = false) private LocalDateTime atualizadoEm;
    @PrePersist void prePersist() { criadoEm = atualizadoEm = LocalDateTime.now(); }
    @PreUpdate void preUpdate() { atualizadoEm = LocalDateTime.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public TipoTarefa getTipo() { return tipo; }
    public void setTipo(TipoTarefa tipo) { this.tipo = tipo; }
    public ClassificacaoTarefa getClassificacao() { return classificacao; }
    public void setClassificacao(ClassificacaoTarefa classificacao) { this.classificacao = classificacao; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}