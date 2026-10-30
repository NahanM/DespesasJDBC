CREATE TABLE IF NOT EXISTS categoria (
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS despesa (
    id           SERIAL PRIMARY KEY,
    descricao    VARCHAR(255)   NOT NULL,
    valor        NUMERIC(10, 2) NOT NULL,
    data         DATE           NOT NULL,
    categoria_id INTEGER        NOT NULL REFERENCES categoria (id)
);