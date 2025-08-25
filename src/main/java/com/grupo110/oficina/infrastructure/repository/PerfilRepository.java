package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Perfil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PerfilRepository implements PanacheRepository<Perfil> {
    
    /**
     * Busca perfil por nome
     */
    public Optional<Perfil> findByNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }

    /**
     * Busca perfis por tipo
     */
    public List<Perfil> findByTipo(Perfil.TipoPerfil tipo) {
        return find("tipo", tipo).list();
    }
    
    /**
     * Busca perfis ativos
     */
    public List<Perfil> findAtivos() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca perfis por tipo ativos
     */
    public List<Perfil> findByTipoAndAtivoTrue(Perfil.TipoPerfil tipo) {
        return find("tipo = ?1 and ativo = ?2", tipo, true).list();
    }
    
    /**
     * Verifica se existe perfil com o nome informado
     */
    public boolean existsByNome(String nome) {
        return count("nome", nome) > 0;
    }
    
    /**
     * Verifica se existe perfil com o tipo informado
     */
    public boolean existsByTipo(Perfil.TipoPerfil tipo) {
        return count("tipo", tipo) > 0;
    }
    
    /**
     * Busca perfil por nome ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Perfil> findByNomeAndIdNot(String nome, Long id) {
        return find("nome = ?1 and id != ?2", nome, id).firstResultOptional();
    }
    
    /**
     * Busca perfis ordenados por nome
     */
    public List<Perfil> findOrderByNome() {
        return find("order by nome").list();
    }
    
    /**
     * Busca perfis ordenados por tipo
     */
    public List<Perfil> findOrderByTipo() {
        return find("order by tipo").list();
    }
    
    /**
     * Conta perfis ativos
     */
    public long countByAtivoTrue() {
        return count("ativo", true);
    }
    
    /**
     * Conta perfis por tipo
     */
    public long countByTipo(Perfil.TipoPerfil tipo) {
        return count("tipo", tipo);
    }
    
    /**
     * Busca perfis que possuem determinada permissão
     */
    public List<Perfil> findByPermissoesNome(String nomePermissao) {
        return find("from Perfil p join p.permissoes perm where perm.nome = ?1", nomePermissao).list();
    }
    
    /**
     * Busca perfis que possuem determinado tipo de permissão
     */
    public List<Perfil> findByPermissoesTipo(String tipoPermissao) {
        return find("from Perfil p join p.permissoes perm where perm.tipo = ?1", tipoPermissao).list();
    }
} 