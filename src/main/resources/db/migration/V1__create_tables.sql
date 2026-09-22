CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    username VARCHAR(60) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('SUPERVISOR', 'OPERADOR')),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_usuarios_username UNIQUE (username)
);

CREATE TABLE tarefas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('DIARIA', 'SEMANAL', 'QUINZENAL', 'MENSAL')),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

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
CREATE INDEX idx_usuarios_perfil ON usuarios(perfil);
