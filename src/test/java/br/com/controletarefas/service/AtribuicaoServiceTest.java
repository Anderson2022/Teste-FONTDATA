package br.com.controletarefas.service;

import br.com.controletarefas.entity.UsuarioTarefa;
import br.com.controletarefas.exception.RegraNegocioException;
import br.com.controletarefas.repository.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtribuicaoServiceTest {
    private final UsuarioTarefaRepository atribuicoes = mock(UsuarioTarefaRepository.class);
    private final UsuarioRepository usuarios = mock(UsuarioRepository.class);
    private final TarefaRepository tarefas = mock(TarefaRepository.class);
    private final AtribuicaoService service = new AtribuicaoService(atribuicoes, usuarios, tarefas);

    @Test void deveImpedirAtribuicaoDuplicada() {
        when(atribuicoes.existsByUsuarioIdAndTarefaId(1L, 2L)).thenReturn(true);
        assertThatThrownBy(() -> service.atribuir(1L, 2L, "admin"))
                .isInstanceOf(RegraNegocioException.class).hasMessage("Tarefa já atribuída ao usuário.");
        verify(atribuicoes, never()).save(any());
    }

    @Test void deveBuscarTarefasDoUsuario() {
        List<UsuarioTarefa> esperado = List.of(new UsuarioTarefa());
        when(atribuicoes.findByUsuarioIdOrderByTarefaNome(1L)).thenReturn(esperado);
        assertThat(service.buscarPorUsuario(1L)).isSameAs(esperado);
    }
}
