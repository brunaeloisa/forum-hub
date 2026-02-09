package br.com.alura.forumhub.domain.topico;

import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.dto.topico.DadosCadastroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao;

    @Enumerated(value = EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas;
}


