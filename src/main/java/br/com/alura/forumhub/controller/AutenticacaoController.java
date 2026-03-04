package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.dto.usuario.DadosAutenticacao;
import br.com.alura.forumhub.infra.security.DtoTokenJWT;
import br.com.alura.forumhub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DtoTokenJWT> fazerLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var tokenAutenticacao = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var autenticacao = manager.authenticate(tokenAutenticacao);
        var tokenJWT = tokenService.generateToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new DtoTokenJWT(tokenJWT));
    }
}
