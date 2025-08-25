package com.grupo110.oficina.interfaces.rest;

import com.grupo110.oficina.application.service.OrdemServicoService;
import com.grupo110.oficina.domain.model.OrdemServico;
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

@Path("/api/ordens-servico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Ordens de Serviço", description = "Operações para gerenciamento de ordens de serviço")
public class OrdemServicoResource {

    @Inject
    OrdemServicoService ordemServicoService;

    @POST
    @Transactional
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Operation(summary = "Criar ordem de serviço", description = "Cria uma nova ordem de serviço na base de dados")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Ordem de serviço criada com sucesso",
            content = @Content(schema = @Schema(implementation = OrdemServico.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "404", description = "Cliente ou veículo não encontrado")
    })
    public Response criarOrdemServico(@Valid OrdemServico ordemServico) {
        try {
            OrdemServico ordemCriada = ordemServicoService.criarOrdemServico(ordemServico);
            return Response.status(Response.Status.CREATED)
                    .entity(ordemCriada)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Operation(summary = "Listar ordens de serviço", description = "Lista todas as ordens de serviço com filtros opcionais")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Lista de ordens de serviço retornada com sucesso",
            content = @Content(schema = @Schema(implementation = OrdemServico.class)))
    })
    public Response listarOrdensServico(
            @Parameter(description = "Filtrar por cliente ID") @QueryParam("clienteId") Long clienteId,
            @Parameter(description = "Filtrar por veículo ID") @QueryParam("veiculoId") Long veiculoId,
            @Parameter(description = "Filtrar por status") @QueryParam("status") String status,
            @Parameter(description = "Filtrar por data inicial") @QueryParam("dataInicial") String dataInicial,
            @Parameter(description = "Filtrar por data final") @QueryParam("dataFinal") String dataFinal,
            @Parameter(description = "Filtrar por ativo") @QueryParam("ativo") Boolean ativo) {
        
        List<OrdemServico> ordens = ordemServicoService.listarTodas();
        
        // Aplicar filtros se fornecidos
        if (clienteId != null) {
            ordens = ordens.stream()
                    .filter(o -> o.getCliente().getId().equals(clienteId))
                    .toList();
        }
        
        if (veiculoId != null) {
            ordens = ordens.stream()
                    .filter(o -> o.getVeiculo().getId().equals(veiculoId))
                    .toList();
        }
        
        if (status != null && !status.trim().isEmpty()) {
            ordens = ordens.stream()
                    .filter(o -> o.getStatus().toString().equalsIgnoreCase(status))
                    .toList();
        }
        
        // Filtro por ativo removido - OrdemServico não possui campo ativo
        
        return Response.ok(ordens).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Operation(summary = "Buscar ordem de serviço por ID", description = "Retorna uma ordem de serviço específica pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Ordem de serviço encontrada",
            content = @Content(schema = @Schema(implementation = OrdemServico.class))),
        @APIResponse(responseCode = "404", description = "Ordem de serviço não encontrada")
    })
    public Response buscarOrdemServicoPorId(
            @Parameter(description = "ID da ordem de serviço") @PathParam("id") Long id) {
        try {
            OrdemServico ordem = ordemServicoService.buscarPorId(id);
            return Response.ok(ordem).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO", "CLIENTE"})
    @Path("/cliente/{clienteId}")
    @Operation(summary = "Listar ordens por cliente", description = "Retorna todas as ordens de serviço de um cliente específico")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Ordens do cliente retornadas com sucesso",
            content = @Content(schema = @Schema(implementation = OrdemServico.class))),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response listarOrdensPorCliente(
            @Parameter(description = "ID do cliente") @PathParam("clienteId") Long clienteId) {
        try {
            List<OrdemServico> ordens = ordemServicoService.listarPorCliente(clienteId);
            return Response.ok(ordens).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/veiculo/{veiculoId}")
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Operation(summary = "Listar ordens por veículo", description = "Retorna todas as ordens de serviço de um veículo específico")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Ordens do veículo retornadas com sucesso",
            content = @Content(schema = @Schema(implementation = OrdemServico.class))),
        @APIResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public Response listarOrdensPorVeiculo(
            @Parameter(description = "ID do veículo") @PathParam("veiculoId") Long veiculoId) {
        try {
            List<OrdemServico> ordens = ordemServicoService.listarPorVeiculo(veiculoId);
            return Response.ok(ordens).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Operation(summary = "Atualizar ordem de serviço", description = "Atualiza uma ordem de serviço existente")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Ordem de serviço atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = OrdemServico.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "404", description = "Ordem de serviço não encontrada")
    })
    public Response atualizarOrdemServico(
            @Parameter(description = "ID da ordem de serviço") @PathParam("id") Long id,
            @Valid OrdemServico ordemAtualizada) {
        try {
            OrdemServico ordem = ordemServicoService.atualizarOrdemServico(id, ordemAtualizada);
            return Response.ok(ordem).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            }
        }
    }

    @PATCH
    @Path("/{id}/iniciar")
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Transactional
    @Operation(summary = "Iniciar execução da ordem", description = "Inicia a execução de uma ordem de serviço")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Execução iniciada com sucesso",
            content = @Content(schema = @Schema(implementation = OrdemServico.class))),
        @APIResponse(responseCode = "400", description = "Status inválido para iniciar"),
        @APIResponse(responseCode = "404", description = "Ordem de serviço não encontrada")
    })
    public Response iniciarExecucao(
            @Parameter(description = "ID da ordem de serviço") @PathParam("id") Long id) {
        try {
            OrdemServico ordem = ordemServicoService.iniciarExecucao(id);
            return Response.ok(ordem).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            }
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"ADMIN", "GERENTE"})
    @Operation(summary = "Remover ordem de serviço", description = "Remove uma ordem de serviço da base de dados")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Ordem de serviço removida com sucesso"),
        @APIResponse(responseCode = "404", description = "Ordem de serviço não encontrada")
    })
    public Response removerOrdemServico(
            @Parameter(description = "ID da ordem de serviço") @PathParam("id") Long id) {
        try {
            ordemServicoService.removerOrdemServico(id);
            return Response.ok("{\"mensagem\": \"Ordem de serviço removida com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PATCH
    @Path("/{id}/finalizar")
    @RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
    @Transactional
    @Operation(summary = "Finalizar ordem de serviço", description = "Finaliza uma ordem de serviço em execução")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Ordem de serviço finalizada com sucesso"),
        @APIResponse(responseCode = "400", description = "Status inválido para finalizar"),
        @APIResponse(responseCode = "404", description = "Ordem de serviço não encontrada")
    })
    public Response finalizarOrdemServico(
            @Parameter(description = "ID da ordem de serviço") @PathParam("id") Long id) {
        try {
            OrdemServico ordem = ordemServicoService.finalizar(id);
            return Response.ok(ordem).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                        .build();
            }
        }
    }
} 