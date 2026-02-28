package br.com.alura.forumhub.dto.topico;

import br.com.alura.forumhub.domain.topico.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotNull StatusTopico status,
        @NotNull Long cursoId
) {}
