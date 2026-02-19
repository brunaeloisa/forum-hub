package br.com.alura.forumhub.dto.topico;

import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosConsultaTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso
) {
    public DadosConsultaTopico(Topico t) {
        this(t.getId(), t.getTitulo(), t.getMensagem(), t.getDataCriacao(),
                t.getStatus(), t.getAutor().getNome(), t.getCurso().getNome());
    }
}
