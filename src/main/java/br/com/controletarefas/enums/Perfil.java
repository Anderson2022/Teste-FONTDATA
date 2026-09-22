package br.com.controletarefas.enums;

public enum Perfil {
    SUPERVISOR("Supervisor"), OPERADOR("Operador");

    private final String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
