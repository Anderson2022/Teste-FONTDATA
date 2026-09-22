CREATE TABLE tipos_tarefa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_tipos_tarefa_nome UNIQUE (nome)
);

INSERT INTO tipos_tarefa (nome) VALUES
    ('Diária'),
    ('Semanal'),
    ('Quinzenal'),
    ('Mensal');