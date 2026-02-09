CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE perfis (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE,

    PRIMARY KEY(id)
);

CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);

CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL,
    status VARCHAR(30) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,

    PRIMARY KEY(id),

    CONSTRAINT fk_topicos_autor_id
        FOREIGN KEY (autor_id) REFERENCES usuarios(id),

    CONSTRAINT fk_topicos_curso_id
        FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE respostas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem TEXT NOT NULL,
    data_criacao DATETIME NOT NULL,
    solucao TINYINT(1) NOT NULL DEFAULT 0,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,

    PRIMARY KEY(id),

    CONSTRAINT fk_respostas_topico_id
        FOREIGN KEY (topico_id) REFERENCES topicos(id),

    CONSTRAINT fk_respostas_autor_id
        FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
