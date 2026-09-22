package br.com.controletarefas.service;

import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.enums.TipoTarefa;
import br.com.controletarefas.repository.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TarefaServiceTest {
    @Test void deveCadastrarTarefa() {
        TarefaRepository repository = mock(TarefaRepository.class);
        Tarefa tarefa = new Tarefa(); tarefa.setNome("Conferir estoque"); tarefa.setTipo(TipoTarefa.DIARIA);
        when(repository.save(tarefa)).thenReturn(tarefa);
        Tarefa salva = new TarefaService(repository, mock(UsuarioTarefaRepository.class)).salvar(tarefa);
        assertThat(salva).isSameAs(tarefa);
        verify(repository).save(tarefa);
    }
}
