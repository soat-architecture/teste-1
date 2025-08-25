package com.grupo110.oficina.application.service;

import java.util.Arrays;
import java.util.HashSet;

import jakarta.enterprise.context.ApplicationScoped;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenService {

    /**
     * Gera um token JWT para um usuário com suas respectivas permissões (roles).
     * @param username O nome do usuário (subject do token).
     * @param roles As permissões do usuário (groups/roles).
     * @return Uma string com o token JWT assinado.
     */
    public String generateToken(String username, String... roles) {
        HashSet<String> rolesSet = new HashSet<>(Arrays.asList(roles));
        long durationInSeconds = 3600; // O token expira em 1 hora

        return Jwt.issuer("https://oficina.com") // O emissor do token
                .subject(username) // O "dono" do token
                .groups(rolesSet) // As permissões
                .expiresIn(durationInSeconds)
                .sign(); // Assina o token com a privateKey.pem configurada
    }
}