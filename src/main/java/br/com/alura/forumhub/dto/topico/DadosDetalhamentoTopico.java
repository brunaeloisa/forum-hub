package br.com.alura.forumhub.dto.topico;

import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        Long autorId,
        Long cursoId
) {
    public DadosDetalhamentoTopico(Topico t) {
        this(t.getId(), t.getTitulo(), t.getMensagem(), t.getDataCriacao(),
                t.getStatus(), t.getAutor().getId(), t.getCurso().getId());
    }
}
