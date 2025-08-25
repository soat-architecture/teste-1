package com.grupo110.oficina.interfaces.rest;

import com.grupo110.oficina.application.service.PecaService;
import com.grupo110.oficina.domain.model.Peca;
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

@Path("/api/pecas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
@Tag(name = "Peças", description = "Operações para gerenciamento de peças")
public class PecaResource {

    @Inject
    PecaService pecaService;

    @POST
    @Transactional
    @Operation(summary = "Criar peça", description = "Cria uma nova peça na base de dados")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Peça criada com sucesso",
            content = @Content(schema = @Schema(implementation = Peca.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "409", description = "Peça já existe com código informado")
    })
    public Response criarPeca(@Valid Peca peca) {
        try {
            Peca pecaCriada = pecaService.criarPeca(peca);
            return Response.status(Response.Status.CREATED)
                    .entity(pecaCriada)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Listar peças", description = "Lista todas as peças com filtros opcionais")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Lista de peças retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Peca.class)))
    })
    public Response listarPecas(
            @Parameter(description = "Filtrar por nome") @QueryParam("nome") String nome,
            @Parameter(description = "Filtrar por categoria") @QueryParam("categoria") String categoria,
            @Parameter(description = "Filtrar por marca do veículo") @QueryParam("marca") String marca,
            @Parameter(description = "Filtrar por ativo") @QueryParam("ativo") Boolean ativo) {
        
        List<Peca> pecas = pecaService.listarTodas();
        
        // Aplicar filtros se fornecidos
        if (nome != null && !nome.trim().isEmpty()) {
            pecas = pecas.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .toList();
        }
        
        if (categoria != null && !categoria.trim().isEmpty()) {
            pecas = pecas.stream()
                    .filter(p -> p.getCategoria() != null && p.getCategoria().name().toLowerCase().contains(categoria.toLowerCase()))
                    .toList();
        }
        
        if (marca != null && !marca.trim().isEmpty()) {
            pecas = pecas.stream()
                    .filter(p -> p.getMarcaVeiculo() != null && p.getMarcaVeiculo().toLowerCase().contains(marca.toLowerCase()))
                    .toList();
        }
        
        if (ativo != null) {
            pecas = pecas.stream()
                    .filter(p -> p.getAtivo().equals(ativo))
                    .toList();
        }
        
        return Response.ok(pecas).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar peça por ID", description = "Retorna uma peça específica pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Peça encontrada",
            content = @Content(schema = @Schema(implementation = Peca.class))),
        @APIResponse(responseCode = "404", description = "Peça não encontrada")
    })
    public Response buscarPecaPorId(
            @Parameter(description = "ID da peça") @PathParam("id") Long id) {
        try {
            Peca peca = pecaService.buscarPorId(id);
            return Response.ok(peca).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar peça", description = "Atualiza uma peça existente")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Peça atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = Peca.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "404", description = "Peça não encontrada"),
        @APIResponse(responseCode = "409", description = "Conflito com código existente")
    })
    public Response atualizarPeca(
            @Parameter(description = "ID da peça") @PathParam("id") Long id,
            @Valid Peca pecaAtualizada) {
        try {
            Peca peca = pecaService.atualizarPeca(id, pecaAtualizada);
            return Response.ok(peca).build();
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
    @Operation(summary = "Desativar peça", description = "Desativa uma peça (soft delete)")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Peça desativada com sucesso"),
        @APIResponse(responseCode = "404", description = "Peça não encontrada")
    })
    public Response desativarPeca(
            @Parameter(description = "ID da peça") @PathParam("id") Long id) {
        try {
            pecaService.desativarPeca(id);
            return Response.ok("{\"mensagem\": \"Peça desativada com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
} 