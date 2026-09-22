package br.com.controletarefas.service;

import br.com.controletarefas.dto.DashboardResumo;
import br.com.controletarefas.enums.Perfil;
import br.com.controletarefas.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {
    private final UsuarioRepository usuarios;
    private final TarefaRepository tarefas;
    private final UsuarioTarefaRepository atribuicoes;

    public DashboardService(UsuarioRepository usuarios, TarefaRepository tarefas, UsuarioTarefaRepository atribuicoes) {
        this.usuarios = usuarios;
        this.tarefas = tarefas;
        this.atribuicoes = atribuicoes;
    }

    @Transactional(readOnly = true)
    public DashboardResumo resumo() {
        return new DashboardResumo(usuarios.count(), tarefas.count(), atribuicoes.count(),
                usuarios.countByPerfil(Perfil.SUPERVISOR), usuarios.countByPerfil(Perfil.OPERADOR));
    }
}
