package br.com.alura.forumhub.repository;

import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagemAndAtivoTrue(String titulo, String mensagem);

    @Query("""
        SELECT t FROM Topico t
        WHERE t.ativo = true
        AND (:ano IS NULL OR YEAR(t.dataCriacao) = :ano)
        AND (:curso IS NULL OR t.curso.nome LIKE %:curso%)
        AND (:status IS NULL OR t.status = :status)
    """)
    Page<Topico> filtrar(
            @Param("ano") Integer ano,
            @Param("curso") String curso,
            @Param("status") StatusTopico status,
            Pageable paginacao
    );

    Optional<Topico> findByIdAndAtivoTrue(Long id);
}
