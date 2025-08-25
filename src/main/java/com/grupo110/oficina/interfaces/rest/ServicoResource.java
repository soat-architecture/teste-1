package com.grupo110.oficina.interfaces.rest;

import com.grupo110.oficina.application.service.ServicoService;
import com.grupo110.oficina.domain.model.Servico;
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

import java.math.BigDecimal;
import java.util.List;

@Path("/api/servicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN", "GERENTE", "ATENDENTE", "MECANICO"})
@Tag(name = "Serviços", description = "Operações para gerenciamento de serviços")
public class ServicoResource {

    @Inject
    ServicoService servicoService;

    @POST
    @Transactional
    @Operation(summary = "Criar serviço", description = "Cria um novo serviço na base de dados")
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Serviço criado com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "409", description = "Serviço já existe com nome informado")
    })
    public Response criarServico(@Valid Servico servico) {
        try {
            Servico servicoCriado = servicoService.criarServico(servico);
            return Response.status(Response.Status.CREATED)
                    .entity(servicoCriado)
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Operation(summary = "Listar serviços", description = "Lista todos os serviços com filtros e ordenação opcionais")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class)))
    })
    public Response listarServicos(
            @Parameter(description = "Filtrar por nome") @QueryParam("nome") String nome,
            @Parameter(description = "Filtrar por categoria") @QueryParam("categoria") String categoria,
            @Parameter(description = "Filtrar por valor base mínimo") @QueryParam("valorMin") BigDecimal valorMin,
            @Parameter(description = "Filtrar por valor base máximo") @QueryParam("valorMax") BigDecimal valorMax,
            @Parameter(description = "Filtrar por tempo de execução mínimo (minutos)") @QueryParam("tempoMin") Integer tempoMin,
            @Parameter(description = "Filtrar por tempo de execução máximo (minutos)") @QueryParam("tempoMax") Integer tempoMax,
            @Parameter(description = "Filtrar por ativo") @QueryParam("ativo") Boolean ativo,
            @Parameter(description = "Ordenar por") @QueryParam("ordenarPor") String ordenarPor) {
        
        List<Servico> servicos = servicoService.listarTodos();
        
        // Aplicar filtros se fornecidos
        if (nome != null && !nome.trim().isEmpty()) {
            servicos = servicos.stream()
                    .filter(s -> s.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .toList();
        }
        
        if (categoria != null && !categoria.trim().isEmpty()) {
            servicos = servicos.stream()
                    .filter(s -> s.getCategoria() != null && s.getCategoria().name().toLowerCase().contains(categoria.toLowerCase()))
                    .toList();
        }
        
        if (valorMin != null) {
            servicos = servicos.stream()
                    .filter(s -> s.getValorBase() != null && s.getValorBase().compareTo(valorMin) >= 0)
                    .toList();
        }
        
        if (valorMax != null) {
            servicos = servicos.stream()
                    .filter(s -> s.getValorBase() != null && s.getValorBase().compareTo(valorMax) <= 0)
                    .toList();
        }
        
        if (tempoMin != null) {
            servicos = servicos.stream()
                    .filter(s -> s.getTempoMedioExecucao() != null && s.getTempoMedioExecucao() >= tempoMin)
                    .toList();
        }
        
        if (tempoMax != null) {
            servicos = servicos.stream()
                    .filter(s -> s.getTempoMedioExecucao() != null && s.getTempoMedioExecucao() <= tempoMax)
                    .toList();
        }
        
        if (ativo != null) {
            servicos = servicos.stream()
                    .filter(s -> s.getAtivo().equals(ativo))
                    .toList();
        }
        
        // Aplicar ordenação se fornecida
        if (ordenarPor != null && !ordenarPor.trim().isEmpty()) {
            switch (ordenarPor.toLowerCase()) {
                case "nome":
                    servicos = servicoService.listarOrdenadosPorNome();
                    break;
                case "valor_asc":
                    servicos = servicoService.listarOrdenadosPorValorBaseAsc();
                    break;
                case "valor_desc":
                    servicos = servicoService.listarOrdenadosPorValorBaseDesc();
                    break;
                case "categoria_nome":
                    servicos = servicoService.listarOrdenadosPorCategoriaENome();
                    break;
            }
        }
        
        return Response.ok(servicos).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar serviço por ID", description = "Retorna um serviço específico pelo ID")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviço encontrado",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public Response buscarServicoPorId(
            @Parameter(description = "ID do serviço") @PathParam("id") Long id) {
        try {
            Servico servico = servicoService.buscarPorId(id);
            return Response.ok(servico).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/nome/{nome}")
    @Operation(summary = "Buscar serviço por nome", description = "Retorna um serviço pelo nome")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviço encontrado",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public Response buscarServicoPorNome(
            @Parameter(description = "Nome do serviço") @PathParam("nome") String nome) {
        try {
            Servico servico = servicoService.buscarPorNome(nome);
            return Response.ok(servico).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/categoria/{categoria}")
    @Operation(summary = "Listar serviços por categoria", description = "Retorna todos os serviços de uma categoria específica")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviços da categoria retornados com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "400", description = "Categoria inválida")
    })
    public Response listarServicosPorCategoria(
            @Parameter(description = "Categoria dos serviços") @PathParam("categoria") String categoria) {
        try {
            Servico.CategoriaServico categoriaEnum = Servico.CategoriaServico.valueOf(categoria.toUpperCase());
            List<Servico> servicos = servicoService.listarPorCategoria(categoriaEnum);
            return Response.ok(servicos).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"erro\": \"Categoria inválida: " + categoria + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/ativos")
    @Operation(summary = "Listar serviços ativos", description = "Retorna apenas os serviços ativos")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviços ativos retornados com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class)))
    })
    public Response listarServicosAtivos() {
        List<Servico> servicos = servicoService.listarAtivos();
        return Response.ok(servicos).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar serviço", description = "Atualiza um serviço existente")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviço atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "400", description = "Dados inválidos"),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado"),
        @APIResponse(responseCode = "409", description = "Conflito com nome existente")
    })
    public Response atualizarServico(
            @Parameter(description = "ID do serviço") @PathParam("id") Long id,
            @Valid Servico servicoAtualizado) {
        try {
            Servico servico = servicoService.atualizarServico(id, servicoAtualizado);
            return Response.ok(servico).build();
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

    @PATCH
    @Path("/{id}/valor")
    @Transactional
    @Operation(summary = "Atualizar valor base", description = "Atualiza apenas o valor base de um serviço")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Valor atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "400", description = "Valor inválido"),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public Response atualizarValorBase(
            @Parameter(description = "ID do serviço") @PathParam("id") Long id,
            @Parameter(description = "Novo valor base") @QueryParam("valor") BigDecimal novoValor) {
        try {
            Servico servico = servicoService.atualizarValorBase(id, novoValor);
            return Response.ok(servico).build();
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
    @Path("/{id}/tempo")
    @Transactional
    @Operation(summary = "Atualizar tempo de execução", description = "Atualiza apenas o tempo médio de execução de um serviço")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Tempo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Servico.class))),
        @APIResponse(responseCode = "400", description = "Tempo inválido"),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public Response atualizarTempoExecucao(
            @Parameter(description = "ID do serviço") @PathParam("id") Long id,
            @Parameter(description = "Novo tempo em minutos") @QueryParam("tempo") Integer novoTempo) {
        try {
            Servico servico = servicoService.atualizarTempoExecucao(id, novoTempo);
            return Response.ok(servico).build();
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
    @Operation(summary = "Desativar serviço", description = "Desativa um serviço (soft delete)")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviço desativado com sucesso"),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public Response desativarServico(
            @Parameter(description = "ID do serviço") @PathParam("id") Long id) {
        try {
            servicoService.desativarServico(id);
            return Response.ok("{\"mensagem\": \"Serviço desativado com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    @Transactional
    @Operation(summary = "Ativar serviço", description = "Ativa um serviço previamente desativado")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Serviço ativado com sucesso"),
        @APIResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    public Response ativarServico(
            @Parameter(description = "ID do serviço") @PathParam("id") Long id) {
        try {
            servicoService.ativarServico(id);
            return Response.ok("{\"mensagem\": \"Serviço ativado com sucesso\"}").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/estatisticas")
    @Operation(summary = "Estatísticas dos serviços", description = "Retorna estatísticas gerais dos serviços")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso")
    })
    public Response obterEstatisticas() {
        long totalAtivos = servicoService.contarAtivos();
        long totalGeral = servicoService.listarTodos().size();
        
        String estatisticas = String.format(
            "{\"total_servicos\": %d, \"servicos_ativos\": %d, \"servicos_inativos\": %d}",
            totalGeral, totalAtivos, (totalGeral - totalAtivos)
        );
        
        return Response.ok(estatisticas).build();
    }
} 