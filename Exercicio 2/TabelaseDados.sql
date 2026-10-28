DROP TABLE IF EXISTS emprestimo, usuario, item;

CREATE TABLE item (
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo      VARCHAR(20)  NOT NULL UNIQUE,
    titulo      VARCHAR(200) NOT NULL,
    tipo        VARCHAR(10)  NOT NULL CHECK (tipo IN ('LIVRO', 'REVISTA')),
    autor       VARCHAR(150),
    edicao      INTEGER,
    disponivel  BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE usuario (
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome          VARCHAR(150) NOT NULL,
    tipo          VARCHAR(10)  NOT NULL CHECK (tipo IN ('ALUNO', 'PROFESSOR')),
    limite_itens  INTEGER      NOT NULL CHECK (limite_itens > 0)
);

CREATE TABLE emprestimo (
    id                       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    item_id                  INTEGER NOT NULL REFERENCES item(id),
    usuario_id               INTEGER NOT NULL REFERENCES usuario(id),
    data_retirada            DATE    NOT NULL,
    data_devolucao_prevista  DATE    NOT NULL,
    data_devolucao           DATE,                          -- NULL = em aberto
    valor_multa              NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (valor_multa >= 0),
    CHECK (data_devolucao_prevista >= data_retirada),
    CHECK (data_devolucao IS NULL OR data_devolucao >= data_retirada)
);

INSERT INTO item (codigo, titulo, tipo, autor, edicao, disponivel) VALUES
    ('L001', 'Dom Casmurro',               'LIVRO',   'Machado de Assis', NULL, FALSE),
    ('L002', 'O Cortiço',                  'LIVRO',   'Aluísio Azevedo',  NULL, TRUE),
    ('L003', 'Vidas Secas',                'LIVRO',   'Graciliano Ramos', NULL, TRUE),
    ('R001', 'Superinteressante',          'REVISTA', NULL,               450,  TRUE),
    ('R002', 'National Geographic Brasil', 'REVISTA', NULL,               312,  TRUE);

INSERT INTO usuario (nome, tipo, limite_itens) VALUES
    ('Ana Souza',   'ALUNO',     3),
    ('Carlos Lima', 'PROFESSOR', 5);

INSERT INTO emprestimo
    (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa)
VALUES
    -- em aberto
    ((SELECT id FROM item WHERE codigo = 'L001'),
     (SELECT id FROM usuario WHERE nome = 'Ana Souza'),
     '2026-10-15', '2026-10-29', NULL, 0.00),
    -- devolvido com 3 dias de atraso (revista: R$ 1,00/dia)
    ((SELECT id FROM item WHERE codigo = 'R001'),
     (SELECT id FROM usuario WHERE nome = 'Ana Souza'),
     '2026-09-01', '2026-09-08', '2026-09-11', 3.00),
    -- devolvido no prazo
    ((SELECT id FROM item WHERE codigo = 'L002'),
     (SELECT id FROM usuario WHERE nome = 'Carlos Lima'),
     '2026-09-10', '2026-09-24', '2026-09-20', 0.00);