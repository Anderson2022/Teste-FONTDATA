CREATE TABLE usuario_tarefa (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    tarefa_id BIGINT NOT NULL,
    atribuido_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atribuido_por BIGINT NOT NULL,
    CONSTRAINT fk_usuario_tarefa_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_usuario_tarefa_tarefa FOREIGN KEY (tarefa_id) REFERENCES tarefas(id),
    CONSTRAINT fk_usuario_tarefa_autor FOREIGN KEY (atribuido_por) REFERENCES usuarios(id),
    CONSTRAINT uk_usuario_tarefa UNIQUE (usuario_id, tarefa_id)
);

CREATE INDEX idx_usuario_tarefa_usuario ON usuario_tarefa(usuario_id);
CREATE INDEX idx_usuario_tarefa_tarefa ON usuario_tarefa(tarefa_id);
