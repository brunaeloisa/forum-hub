CREATE TABLE usuarios_perfis (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY (usuario_id, perfil_id),

    CONSTRAINT fk_usuarios_perfis_usuario_id
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id),

    CONSTRAINT fk_usuarios_perfis_perfil_id
        FOREIGN KEY (perfil_id) REFERENCES perfis(id)
);