package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.dto.topico.DadosAtualizacaoTopico;
import br.com.alura.forumhub.dto.topico.DadosCadastroTopico;
import br.com.alura.forumhub.dto.topico.DadosTopicoCriado;
import br.com.alura.forumhub.dto.topico.DadosConsultaTopico;
import br.com.alura.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<DadosTopicoCriado> cadastrar(@RequestBody @Valid DadosCadastroTopico dados,
                                                       UriComponentsBuilder uriBuilder) {
        var topico = topicoService.cadastrar(dados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosTopicoCriado(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosConsultaTopico>> listar(
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) StatusTopico status,
            @PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao
    ) {
        var pagina = topicoService.listar(ano, curso, status, paginacao);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosConsultaTopico> detalhar(@PathVariable Long id) {
        var topico = topicoService.detalhar(id);
        return ResponseEntity.ok(new DadosConsultaTopico(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosConsultaTopico> atualizar(@PathVariable Long id,
                                                         @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topicoAtualizado = topicoService.atualizar(id, dados);
        return ResponseEntity.ok(new DadosConsultaTopico(topicoAtualizado));
    }
}
