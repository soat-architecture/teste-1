package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    
    /**
     * Busca cliente por documento (CPF/CNPJ)
     */
    public Optional<Cliente> findByDocumento(String documento) {
        return find("documento", documento).firstResultOptional();
    }
    
    /**
     * Busca cliente por email
     */
    public Optional<Cliente> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
    
    /**
     * Busca clientes por nome (busca parcial)
     */
    public List<Cliente> findByNomeContainingIgnoreCase(String nome) {
        return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
    
    /**
     * Busca clientes ativos
     */
    public List<Cliente> findAtivos() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca clientes por tipo de documento
     */
    public List<Cliente> findByTipoDocumento(Cliente.TipoDocumento tipoDocumento) {
        return find("tipoDocumento", tipoDocumento).list();
    }
    
    /**
     * Verifica se existe cliente com o documento informado
     */
    public boolean existsByDocumento(String documento) {
        return count("documento", documento) > 0;
    }
    
    /**
     * Verifica se existe cliente com o email informado
     */
    public boolean existsByEmail(String email) {
        return count("email", email) > 0;
    }
    
    /**
     * Busca cliente por documento ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Cliente> findByDocumentoAndIdNot(String documento, Long id) {
        return find("documento = ?1 and id != ?2", documento, id).firstResultOptional();
    }
    
    /**
     * Busca cliente por email ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Cliente> findByEmailAndIdNot(String email, Long id) {
        return find("email = ?1 and id != ?2", email, id).firstResultOptional();
    }
} 