package com.grupo110.oficina.interfaces.rest;

import com.grupo110.oficina.application.service.VeiculoService;
import com.grupo110.oficina.domain.model.Veiculo;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/veiculos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
@Tag(name = "Veículos", description = "Operações para gerenciamento de veículos")
public class VeiculoResource {

    @Inject
    VeiculoService veiculoService;

    @POST
    @Transactional
    @Operation(summary = "Criar veículo", description = "Cria um novo veículo na base de dados")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Veículo criado com sucesso",
            content = @Content(schema = @Schema(implementation = Veiculo.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "409", description = "Veículo já existe com placa informada")
    })
    public Response criarVeiculo(@Valid Veiculo veiculo) {
        try {
            Veiculo veiculoCriado = veiculoService.criarVeiculo(veiculo);
            return Response.status(Response.Status.CREATED)
                    .entity(veiculoCriado)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Listar veículos", description = "Lista todos os veículos com filtros opcionais")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Veiculo.class)))
    })
    public Response listarVeiculos(
            @Parameter(description = "Filtrar por placa") @QueryParam("placa") String placa,
            @Parameter(description = "Filtrar por marca") @QueryParam("marca") String marca,
            @Parameter(description = "Filtrar por modelo") @QueryParam("modelo") String modelo,
            @Parameter(description = "Filtrar por cliente ID") @QueryParam("clienteId") Long clienteId,
            @Parameter(description = "Filtrar por ativo") @QueryParam("ativo") Boolean ativo) {
        
        List<Veiculo> veiculos = veiculoService.listarTodos();
        
        // Aplicar filtros se fornecidos
        if (placa != null && !placa.trim().isEmpty()) {
            veiculos = veiculos.stream()
                    .filter(v -> v.getPlaca().toUpperCase().contains(placa.toUpperCase()))
                    .toList();
        }
        
        if (marca != null && !marca.trim().isEmpty()) {
            veiculos = veiculos.stream()
                    .filter(v -> v.getMarca().toLowerCase().contains(marca.toLowerCase()))
                    .toList();
        }
        
        if (modelo != null && !modelo.trim().isEmpty()) {
            veiculos = veiculos.stream()
                    .filter(v -> v.getModelo().toLowerCase().contains(modelo.toLowerCase()))
                    .toList();
        }
        
        if (clienteId != null) {
            veiculos = veiculos.stream()
                    .filter(v -> v.getCliente().getId().equals(clienteId))
                    .toList();
        }
        
        if (ativo != null) {
            veiculos = veiculos.stream()
                    .filter(v -> v.getAtivo().equals(ativo))
                    .toList();
        }
        
        return Response.ok(veiculos).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar veículo por ID", description = "Retorna um veículo específico pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Veículo encontrado",
            content = @Content(schema = @Schema(implementation = Veiculo.class))),
        @APIResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Response buscarVeiculoPorId(
            @Parameter(description = "ID do veículo") @PathParam("id") Long id) {
        try {
            Veiculo veiculo = veiculoService.buscarPorId(id);
            return Response.ok(veiculo).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/placa/{placa}")
    @Operation(summary = "Buscar veículo por placa", description = "Retorna um veículo pela placa")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Veículo encontrado",
            content = @Content(schema = @Schema(implementation = Veiculo.class))),
        @APIResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Response buscarVeiculoPorPlaca(
            @Parameter(description = "Placa do veículo") @PathParam("placa") String placa) {
        try {
            Veiculo veiculo = veiculoService.buscarPorPlaca(placa);
            return Response.ok(veiculo).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/cliente/{clienteId}")
    @Operation(summary = "Listar veículos por cliente", description = "Retorna todos os veículos de um cliente específico")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Veículos do cliente retornados com sucesso",
            content = @Content(schema = @Schema(implementation = Veiculo.class))),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response listarVeiculosPorCliente(
            @Parameter(description = "ID do cliente") @PathParam("clienteId") Long clienteId) {
        try {
            List<Veiculo> veiculos = veiculoService.listarPorCliente(clienteId);
            return Response.ok(veiculos).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar veículo", description = "Atualiza um veículo existente")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Veículo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Veiculo.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "404", description = "Veículo não encontrado"),
        @APIResponse(responseCode = "409", description = "Conflito com placa existente")
    })
    public Response atualizarVeiculo(
            @Parameter(description = "ID do veículo") @PathParam("id") Long id,
            @Valid Veiculo veiculoAtualizado) {
        try {
            Veiculo veiculo = veiculoService.atualizarVeiculo(id, veiculoAtualizado);
            return Response.ok(veiculo).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            }
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Desativar veículo", description = "Desativa um veículo (soft delete)")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Veículo desativado com sucesso"),
        @APIResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Response desativarVeiculo(
            @Parameter(description = "ID do veículo") @PathParam("id") Long id) {
        try {
            veiculoService.desativarVeiculo(id);
            return Response.ok("{\"mensagem\": \"Veículo desativado com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    @Transactional
    @Operation(summary = "Ativar veículo", description = "Ativa um veículo previamente desativado")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Veículo ativado com sucesso"),
        @APIResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Response ativarVeiculo(
            @Parameter(description = "ID do veículo") @PathParam("id") Long id) {
        try {
            veiculoService.ativarVeiculo(id);
            return Response.ok("{\"mensagem\": \"Veículo ativado com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
} 