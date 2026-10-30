-- Garante que os acentos sejam lidos certo (ex.: psql no Windows)
SET client_encoding = 'UTF8';

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

-- Categorias iniciais (só insere as que ainda não existem)
INSERT INTO categoria (nome)
SELECT v.nome
FROM (VALUES ('Alimentação'), ('Transporte'), ('Moradia'), ('Saúde'), ('Lazer'), ('Educação')) AS v (nome)
WHERE NOT EXISTS (SELECT 1 FROM categoria c WHERE c.nome = v.nome);