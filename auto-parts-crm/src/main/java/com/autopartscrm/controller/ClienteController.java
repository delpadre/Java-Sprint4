package com.autopartscrm.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.autopartscrm.model.Cliente;
import com.autopartscrm.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/franqueado/clientes")
@Tag(name = "Franqueado - Clientes", description = "Endpoints de gestão de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos os clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
        @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado")
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listar(Model model) {
        var clientes = clienteService.listarTodos();
        if (Objects.isNull(clientes) || clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        return ResponseEntity.ok(this.clienteService.obterClientePorId(id));
    }

    @Operation(summary = "Contar número total de clientes")
    @ApiResponse(responseCode = "200", description = "Quantidade de clientes retornada")
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(this.clienteService.contarClientes());
    }

    @Operation(summary = "Cadastrar um novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
    })
    @PostMapping("/")
    public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(this.clienteService.salvar(cliente));
    }

    @Operation(summary = "Excluir um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @DeleteMapping("/")
    public ResponseEntity<Void> excluir(@Valid @RequestBody Cliente cliente) {
        this.clienteService.excluir(cliente);
        return ResponseEntity.ok().build();
    }
}
