package com.grupo110.oficina.interfaces.rest;

import com.grupo110.oficina.application.service.ClienteService;
import com.grupo110.oficina.domain.model.Cliente;
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

@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE"})
@Tag(name = "Clientes", description = "Operações para gerenciamento de clientes")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @POST
    @Transactional
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente na base de dados")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Cliente criado com sucesso",
            content = @Content(schema = @Schema(implementation = Cliente.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "409", description = "Cliente já existe com documento/email informado")
    })
    public Response criarCliente(@Valid Cliente cliente) {
        try {
            Cliente clienteCriado = clienteService.criarCliente(cliente);
            return Response.status(Response.Status.CREATED)
                    .entity(clienteCriado)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes com paginação opcional")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Cliente.class)))
    })
    public Response listarClientes(
            @Parameter(description = "Número da página") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Tamanho da página") @QueryParam("size") @DefaultValue("20") int size,
            @Parameter(description = "Filtrar por nome") @QueryParam("nome") String nome,
            @Parameter(description = "Filtrar por documento") @QueryParam("documento") String documento,
            @Parameter(description = "Filtrar por ativo") @QueryParam("ativo") Boolean ativo) {
        
        List<Cliente> clientes = clienteService.listarTodos();
        
        // Aplicar filtros se fornecidos
        if (nome != null && !nome.trim().isEmpty()) {
            clientes = clientes.stream()
                    .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .toList();
        }
        
        if (documento != null && !documento.trim().isEmpty()) {
            clientes = clientes.stream()
                    .filter(c -> c.getDocumento().contains(documento))
                    .toList();
        }
        
        if (ativo != null) {
            clientes = clientes.stream()
                    .filter(c -> c.getAtivo().equals(ativo))
                    .toList();
        }
        
        // Aplicar paginação
        int total = clientes.size();
        int start = page * size;
        int end = Math.min(start + size, total);
        
        List<Cliente> clientesPaginados = clientes.subList(start, end);
        
        return Response.ok(clientesPaginados)
                .header("X-Total-Count", total)
                .header("X-Page", page)
                .header("X-Size", size)
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(schema = @Schema(implementation = Cliente.class))),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response buscarClientePorId(
            @Parameter(description = "ID do cliente") @PathParam("id") Long id) {
        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return Response.ok(cliente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/documento/{documento}")
    @Operation(summary = "Buscar cliente por documento", description = "Retorna um cliente pelo CPF/CNPJ")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(schema = @Schema(implementation = Cliente.class))),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response buscarClientePorDocumento(
            @Parameter(description = "CPF/CNPJ do cliente") @PathParam("documento") String documento) {
        try {
            Cliente cliente = clienteService.buscarPorDocumento(documento);
            return Response.ok(cliente).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar cliente", description = "Atualiza um cliente existente")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Cliente.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado"),
        @APIResponse(responseCode = "409", description = "Conflito com documento/email existente")
    })
    public Response atualizarCliente(
            @Parameter(description = "ID do cliente") @PathParam("id") Long id,
            @Valid Cliente clienteAtualizado) {
        try {
            Cliente cliente = clienteService.atualizarCliente(id, clienteAtualizado);
            return Response.ok(cliente).build();
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
    @Operation(summary = "Desativar cliente", description = "Desativa um cliente (soft delete)")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Cliente desativado com sucesso"),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response desativarCliente(
            @Parameter(description = "ID do cliente") @PathParam("id") Long id) {
        try {
            clienteService.desativarCliente(id);
            return Response.ok("{\"mensagem\": \"Cliente desativado com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    @Transactional
    @Operation(summary = "Ativar cliente", description = "Ativa um cliente previamente desativado")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Cliente ativado com sucesso"),
        @APIResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Response ativarCliente(
            @Parameter(description = "ID do cliente") @PathParam("id") Long id) {
        try {
            clienteService.ativarCliente(id);
            return Response.ok("{\"mensagem\": \"Cliente ativado com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
} 