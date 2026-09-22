UPDATE tarefas tarefa
SET tipo_id = tipo_tarefa.id
FROM tipos_tarefa tipo_tarefa
WHERE tipo_tarefa.nome = CASE tarefa.tipo
    WHEN 'DIARIA' THEN 'Diária'
    WHEN 'SEMANAL' THEN 'Semanal'
    WHEN 'QUINZENAL' THEN 'Quinzenal'
    WHEN 'MENSAL' THEN 'Mensal'
END;

UPDATE tarefas
SET descricao = nome
WHERE descricao IS NULL;

ALTER TABLE tarefas
    ALTER COLUMN descricao SET NOT NULL,
    ALTER COLUMN tipo_id SET NOT NULL,
    ADD CONSTRAINT ck_tarefas_classificacao
        CHECK (classificacao IN ('INTERNA', 'CLIENTE')),
    ADD CONSTRAINT fk_tarefas_tipo
        FOREIGN KEY (tipo_id) REFERENCES tipos_tarefa(id),
    ADD CONSTRAINT fk_tarefas_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    DROP COLUMN tipo;

CREATE INDEX idx_tarefas_tipo ON tarefas(tipo_id);
CREATE INDEX idx_tarefas_cliente ON tarefas(cliente_id);