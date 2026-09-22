package br.com.controletarefas.dto;

import br.com.controletarefas.enums.ClassificacaoTarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TarefaForm {
    private Long id;
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 150)
    private String nome;
    @NotBlank(message = "Descrição é obrigatória.")
    @Size(max = 500)
    private String descricao;
    @NotNull(message = "Tipo é obrigatório.")
    private Long tipoId;
    @NotNull(message = "Classificação é obrigatória.")
    private ClassificacaoTarefa classificacao = ClassificacaoTarefa.INTERNA;
    private Long clienteId;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public ClassificacaoTarefa getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(ClassificacaoTarefa classificacao) {
        this.classificacao = classificacao;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}