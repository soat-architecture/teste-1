package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    
    /**
     * Busca usuário por username
     */
    public Optional<Usuario> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
    
    /**
     * Busca usuário por email
     */
    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
    
    /**
     * Busca usuário por nome (busca parcial)
     */
    public List<Usuario> findByNomeContainingIgnoreCase(String nome) {
        return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
    
    /**
     * Busca usuários ativos
     */
    public List<Usuario> findAtivos() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca usuários por último acesso
     */
    public List<Usuario> findByUltimoAcessoBefore(LocalDateTime data) {
        return find("ultimoAcesso < ?1", data).list();
    }
    
    /**
     * Busca usuários por data de cadastro
     */
    public List<Usuario> findByDataCadastroBetween(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("dataCadastro between ?1 and ?2", dataInicio, dataFim).list();
    }
    
    /**
     * Verifica se existe usuário com o username informado
     */
    public boolean existsByUsername(String username) {
        return count("username", username) > 0;
    }
    
    /**
     * Verifica se existe usuário com o email informado
     */
    public boolean existsByEmail(String email) {
        return count("email", email) > 0;
    }
    
    /**
     * Busca usuário por username ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Usuario> findByUsernameAndIdNot(String username, Long id) {
        return find("username = ?1 and id != ?2", username, id).firstResultOptional();
    }
    
    /**
     * Busca usuário por email ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Usuario> findByEmailAndIdNot(String email, Long id) {
        return find("email = ?1 and id != ?2", email, id).firstResultOptional();
    }
    
    /**
     * Busca usuários ordenados por nome
     */
    public List<Usuario> findOrderByNome() {
        return find("order by nome").list();
    }
    
    /**
     * Busca usuários ordenados por data de cadastro (mais recentes primeiro)
     */
    public List<Usuario> findOrderByDataCadastroDesc() {
        return find("order by dataCadastro desc").list();
    }
    
    /**
     * Busca usuários ordenados por último acesso (mais recentes primeiro)
     */
    public List<Usuario> findOrderByUltimoAcessoDesc() {
        return find("order by ultimoAcesso desc").list();
    }
    
    /**
     * Conta usuários ativos
     */
    public long countByAtivoTrue() {
        return count("ativo", true);
    }
    
    /**
     * Conta usuários por data de cadastro
     */
    public long countByDataCadastroBetween(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return count("dataCadastro between ?1 and ?2", dataInicio, dataFim);
    }
    
    /**
     * Busca usuários que não acessaram há mais de X dias
     */
    public List<Usuario> findUsuariosInativos(int diasLimite) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(diasLimite);
        return find("ultimoAcesso < ?1 or ultimoAcesso is null", dataLimite).list();
    }
    
    /**
     * Busca usuários por perfil
     */
    public List<Usuario> findByPerfisNome(String nomePerfil) {
        return find("from Usuario u join u.perfis p where p.nome = ?1", nomePerfil).list();
    }
    
    /**
     * Busca usuários por tipo de perfil
     */
    public List<Usuario> findByPerfisTipo(String tipoPerfil) {
        return find("from Usuario u join u.perfis p where p.tipo = ?1", tipoPerfil).list();
    }
} 