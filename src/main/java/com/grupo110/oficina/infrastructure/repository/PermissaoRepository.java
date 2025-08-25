package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Permissao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PermissaoRepository implements PanacheRepository<Permissao> {
    
    /**
     * Busca permissão por nome
     */
    public Optional<Permissao> findByNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }
    
    /**
     * Busca permissões por tipo
     */
    public List<Permissao> findByTipo(Permissao.TipoPermissao tipo) {
        return find("tipo", tipo).list();
    }
    
    /**
     * Busca permissões por recurso
     */
    public List<Permissao> findByRecurso(String recurso) {
        return find("recurso", recurso).list();
    }
    
    /**
     * Busca permissões por ação
     */
    public List<Permissao> findByAcao(String acao) {
        return find("acao", acao).list();
    }
    
    /**
     * Busca permissões por recurso e ação
     */
    public List<Permissao> findByRecursoAndAcao(String recurso, String acao) {
        return find("recurso = ?1 and acao = ?2", recurso, acao).list();
    }
    
    /**
     * Busca permissões ativas
     */
    public List<Permissao> findAtivas() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca permissões por tipo ativas
     */
    public List<Permissao> findByTipoAndAtivoTrue(Permissao.TipoPermissao tipo) {
        return find("tipo = ?1 and ativo = ?2", tipo, true).list();
    }
    
    /**
     * Verifica se existe permissão com o nome informado
     */
    public boolean existsByNome(String nome) {
        return count("nome", nome) > 0;
    }
    
    /**
     * Verifica se existe permissão com o tipo informado
     */
    public boolean existsByTipo(Permissao.TipoPermissao tipo) {
        return count("tipo", tipo) > 0;
    }
    
    /**
     * Busca permissão por nome ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Permissao> findByNomeAndIdNot(String nome, Long id) {
        return find("nome = ?1 and id != ?2", nome, id).firstResultOptional();
    }
    
    /**
     * Busca permissões ordenadas por nome
     */
    public List<Permissao> findOrderByNome() {
        return find("order by nome").list();
    }
    
    /**
     * Busca permissões ordenadas por tipo
     */
    public List<Permissao> findOrderByTipo() {
        return find("order by tipo").list();
    }
    
    /**
     * Busca permissões ordenadas por recurso e ação
     */
    public List<Permissao> findOrderByRecursoAscAcaoAsc() {
        return find("order by recurso asc, acao asc").list();
    }
    
    /**
     * Conta permissões ativas
     */
    public long countByAtivoTrue() {
        return count("ativo", true);
    }
    
    /**
     * Conta permissões por tipo
     */
    public long countByTipo(Permissao.TipoPermissao tipo) {
        return count("tipo", tipo);
    }
    
    /**
     * Conta permissões por recurso
     */
    public long countByRecurso(String recurso) {
        return count("recurso", recurso);
    }
    
    /**
     * Busca permissões que pertencem a determinado perfil
     */
    public List<Permissao> findByPerfisNome(String nomePerfil) {
        return find("from Permissao p join p.perfis perf where perf.nome = ?1", nomePerfil).list();
    }
    
    /**
     * Busca permissões que pertencem a determinado tipo de perfil
     */
    public List<Permissao> findByPerfisTipo(String tipoPerfil) {
        return find("from Permissao p join p.perfis perf where perf.tipo = ?1", tipoPerfil).list();
    }
} 