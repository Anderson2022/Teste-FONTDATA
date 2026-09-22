package br.com.controletarefas.enums;

public enum ClassificacaoTarefa {
    INTERNA("Interna"), CLIENTE("Cliente");

    private final String descricao;

    ClassificacaoTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}