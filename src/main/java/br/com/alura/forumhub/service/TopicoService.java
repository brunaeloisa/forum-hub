package br.com.alura.forumhub.service;

import br.com.alura.forumhub.dto.topico.DadosCadastroTopico;
import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.repository.CursoRepository;
import br.com.alura.forumhub.repository.TopicoRepository;
import br.com.alura.forumhub.repository.UsuarioRepository;
import br.com.alura.forumhub.exception.ValidacaoTopicoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicoService {
    
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados) {
        if (topicoRepository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoTopicoException("Tópico duplicado.");
        }

        var curso = cursoRepository.findById(dados.cursoId())
                .orElseThrow(() -> new ValidacaoTopicoException("Id do curso informado não existe."));

        var autor = usuarioRepository.findById(dados.autorId())
                .orElseThrow(() -> new ValidacaoTopicoException("Id do usuário informado não existe."));

        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);
        return topico;
    }
}
