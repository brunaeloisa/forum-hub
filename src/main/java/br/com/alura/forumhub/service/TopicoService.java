package br.com.alura.forumhub.service;

import br.com.alura.forumhub.domain.topico.Curso;
import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.domain.usuario.Perfil;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.dto.topico.DadosAtualizacaoTopico;
import br.com.alura.forumhub.dto.topico.DadosCadastroTopico;
import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.dto.topico.DadosConsultaTopico;
import br.com.alura.forumhub.repository.CursoRepository;
import br.com.alura.forumhub.repository.TopicoRepository;
import br.com.alura.forumhub.repository.UsuarioRepository;
import br.com.alura.forumhub.exception.ValidacaoTopicoException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TopicoService {
    
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados) {
        if (topicoRepository.existsByTituloAndMensagemAndAtivoTrue(dados.titulo(), dados.mensagem())) {
            throw new ValidacaoTopicoException("Tópico duplicado.");
        }

        var curso = buscaCursoPorId(dados.cursoId());
        var autor = buscaAutorPorId(dados.autorId());

        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);
        return topico;
    }

    @Transactional(readOnly = true)
    public Page<DadosConsultaTopico> listar(Integer ano, String curso, StatusTopico status, Pageable paginacao) {
        return topicoRepository.filtrar(ano, curso, status, paginacao).map(DadosConsultaTopico::new);
    }

    @Transactional(readOnly = true)
    public Topico detalhar(Long id) {
        return buscaTopicoPorId(id);
    }

    @Transactional
    public Topico atualizar(Long id, DadosAtualizacaoTopico dados, Usuario usuarioLogado) {
        var topico = buscaTopicoPorId(id);

        verificarPermissao(topico, usuarioLogado);

        var curso = buscaCursoPorId(dados.cursoId());

        topico.atualizarInformacoes(dados.titulo(), dados.mensagem(), dados.status(), curso);
        return topico;
    }

    @Transactional
    public void excluir(Long id, Usuario usuarioLogado) {
        var topico = buscaTopicoPorId(id);

        verificarPermissao(topico, usuarioLogado);

        topico.excluir();
    }

    private void verificarPermissao(Topico topico, Usuario usuarioLogado) {
        if (!temPermissao(topico, usuarioLogado)) {
            throw new AccessDeniedException("Usuário não tem permissão para esta operação");
        }
    }

    private boolean temPermissao(Topico topico, Usuario usuarioLogado) {
        boolean ehAutor = topico.getAutor().getId().equals(usuarioLogado.getId());
        boolean ehInstrutorOuAdmin = usuarioLogado.getPerfis().stream()
                .map(Perfil::getNome)
                .anyMatch(n -> n.equals("ROLE_ADMIN") || n.equals("ROLE_INSTRUTOR"));

        return ehAutor || ehInstrutorOuAdmin;
    }

    private Topico buscaTopicoPorId(Long id) {
        return topicoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado."));
    }

    private Curso buscaCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoTopicoException("Id do curso informado não existe."));
    }

    private Usuario buscaAutorPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoTopicoException("Id do usuário informado não existe."));
    }
}