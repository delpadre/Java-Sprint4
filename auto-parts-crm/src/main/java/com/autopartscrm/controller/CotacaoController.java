package com.autopartscrm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autopartscrm.model.Cotacao;
import com.autopartscrm.service.CotacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cliente/cotacoes")
@RequiredArgsConstructor
@Tag(name = "Cliente - Cotações", description = "Endpoints para solicitação, resposta e aprovação de cotações")
public class CotacaoController {

	private final CotacaoService cotacaoService;

	@Operation(summary = "Listar todas as cotações do cliente")
	@ApiResponse(responseCode = "200", description = "Lista de cotações retornada com sucesso")
	@GetMapping
	public ResponseEntity<List<Cotacao>> listar() {
		return ResponseEntity.ok(cotacaoService.listarTodos());
	}

	@Operation(summary = "Criar uma nova cotação")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Cotação criada com sucesso", content = @Content(schema = @Schema(implementation = Cotacao.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping("/salvar")
	public ResponseEntity<Cotacao> salvar(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da nova cotação", required = true, content = @Content(schema = @Schema(implementation = Cotacao.class))) @Valid @RequestBody Cotacao cotacao) {

		return ResponseEntity.ok(cotacaoService.salvar(cotacao));
	}

	@Operation(summary = "Buscar uma cotação por ID para responder")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Cotação encontrada"),
			@ApiResponse(responseCode = "404", description = "Cotação não encontrada") })
	@GetMapping("/responder/{id}")
	public ResponseEntity<Cotacao> responderForm(@PathVariable Long id) {
		Cotacao cotacao = cotacaoService.listarTodos().stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Cotação não encontrada"));

		return ResponseEntity.ok(cotacao);
	}

	@Operation(summary = "Responder a uma cotação")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Resposta registrada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cotação não encontrada") })
	@PostMapping("/responder/{id}")
	public ResponseEntity<Void> responder(@PathVariable Long id, @RequestParam Double precoProposto,
			@RequestParam String resposta) {

		cotacaoService.responder(id, precoProposto, resposta);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Aprovar uma cotação")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Cotação aprovada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cotação não encontrada") })
	@PostMapping("/aprovar/{id}")
	public ResponseEntity<Void> aprovar(@PathVariable Long id) {
		cotacaoService.aprovar(id);
		return ResponseEntity.ok().build();
	}
}
