package br.com.controletarefas.enums;

public enum TipoTarefa {
    DIARIA("Diária"), SEMANAL("Semanal"), QUINZENAL("Quinzenal"), MENSAL("Mensal");
    private final String descricao;
    TipoTarefa(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
