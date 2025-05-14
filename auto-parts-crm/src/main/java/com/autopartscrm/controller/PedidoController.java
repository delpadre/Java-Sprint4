package com.autopartscrm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autopartscrm.model.Cotacao;
import com.autopartscrm.model.Pedido;
import com.autopartscrm.model.StatusPedido;
import com.autopartscrm.service.CotacaoService;
import com.autopartscrm.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cliente/pedidos")
@RequiredArgsConstructor
@Tag(name = "Cliente - Pedidos", description = "Endpoints de criação e atualização de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final CotacaoService cotacaoService;

    @Operation(summary = "Inicializar pedido com base em uma cotação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido iniciado com sucesso",
            content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Cotação não encontrada")
    })
    @GetMapping("/novo/{cotacaoId}")
    public ResponseEntity<Pedido> novo(@PathVariable Long cotacaoId) {
        Cotacao cotacao = cotacaoService.listarTodos().stream()
                .filter(c -> c.getId().equals(cotacaoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));

        Pedido pedido = Pedido.builder().cotacao(cotacao).build();
        return ResponseEntity.ok(pedido);
    }

    @Operation(summary = "Criar um novo pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
    })
    @PostMapping("/salvar")
    public ResponseEntity<Pedido> salvar(
    		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do pedido", required = true,
            content = @Content(schema = @Schema(implementation = Pedido.class)))
        @Valid @RequestBody Pedido pedido) {

        Pedido criado = pedidoService.criar(pedido);
        return ResponseEntity.ok(criado);
    }

    @Operation(summary = "Atualizar status de um pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PostMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido status) {

        pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
