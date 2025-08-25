package com.grupo110.oficina.interfaces.rest;

import com.grupo110.oficina.application.dto.LoginRequest;
import com.grupo110.oficina.application.service.TokenService;
import com.grupo110.oficina.application.service.UsuarioService;
import com.grupo110.oficina.domain.model.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    TokenService tokenService;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        Usuario usuario = null;
        try {
            usuario = usuarioService.buscarPorUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário ou senha inválidos.").build();
        }
        if (!password.equals(usuario.getSenha())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário ou senha inválidos.").build();
        }
        String token = tokenService.generateToken(username, usuario.getPerfil().getTipo().name());
        return Response.ok(token).build();
    }
}