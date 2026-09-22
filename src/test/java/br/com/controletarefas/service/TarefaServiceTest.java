package br.com.controletarefas.service;

import br.com.controletarefas.dto.TarefaForm;
import br.com.controletarefas.entity.Tarefa;
import br.com.controletarefas.entity.TipoTarefa;
import br.com.controletarefas.enums.ClassificacaoTarefa;
import br.com.controletarefas.repository.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TarefaServiceTest {
    @Test
    void deveCadastrarTarefaInterna() {
        TarefaRepository tarefas = mock(TarefaRepository.class);
        TipoTarefaRepository tipos = mock(TipoTarefaRepository.class);
        TipoTarefa tipo = new TipoTarefa(); tipo.setId(1L); tipo.setNome("Diária"); tipo.setAtivo(true);
        when(tipos.findById(1L)).thenReturn(Optional.of(tipo));
        when(tarefas.save(any(Tarefa.class))).thenAnswer(invocation -> invocation.getArgument(0));
        TarefaForm form = new TarefaForm(); form.setNome("Conferir estoque"); form.setDescricao("Conferir produtos");
        form.setTipoId(1L); form.setClassificacao(ClassificacaoTarefa.INTERNA);
        TarefaService service = new TarefaService(tarefas, mock(UsuarioTarefaRepository.class), tipos, mock(ClienteRepository.class));
        Tarefa salva = service.salvar(form);
        assertThat(salva.getNome()).isEqualTo("Conferir estoque");
        assertThat(salva.getTipo()).isSameAs(tipo); assertThat(salva.getCliente()).isNull();
        verify(tarefas).save(any(Tarefa.class));
    }
}