package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Peca;
import com.grupo110.oficina.domain.model.Servico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<Servico> {
    
    /**
     * Busca serviço por nome
     */
    public Optional<Servico> findByNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }
    
    /**
     * Busca serviços por categoria
     */
    public List<Servico> findByCategoria(Servico.CategoriaServico categoria) {
        return find("categoria", categoria).list();
    }
    
    /**
     * Busca serviços por nome (busca parcial)
     */
    public List<Servico> findByNomeContainingIgnoreCase(String nome) {
        return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
    
    /**
     * Busca serviços ativos
     */
    public List<Servico> findAtivos() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca serviços por categoria ativos
     */
    public List<Servico> findByCategoriaAndAtivoTrue(Servico.CategoriaServico categoria) {
        return find("categoria = ?1 and ativo = ?2", categoria, true).list();
    }
    
    /**
     * Busca serviços por valor base maior que
     */
    public List<Servico> findByValorBaseGreaterThan(BigDecimal valor) {
        return find("valorBase > ?1", valor).list();
    }
    
    /**
     * Busca serviços por valor base entre
     */
    public List<Servico> findByValorBaseBetween(BigDecimal valorMin, BigDecimal valorMax) {
        return find("valorBase between ?1 and ?2", valorMin, valorMax).list();
    }
    
    /**
     * Busca serviços por tempo médio de execução menor que
     */
    public List<Servico> findByTempoMedioExecucaoLessThan(Integer tempoMinutos) {
        return find("tempoMedioExecucao < ?1", tempoMinutos).list();
    }
    
    /**
     * Busca serviços por tempo médio de execução entre
     */
    public List<Servico> findByTempoMedioExecucaoBetween(Integer tempoMin, Integer tempoMax) {
        return find("tempoMedioExecucao between ?1 and ?2", tempoMin, tempoMax).list();
    }
    
    /**
     * Verifica se existe serviço com o nome informado
     */
    public boolean existsByNome(String nome) {
        return count("nome", nome) > 0;
    }
    
    /**
     * Busca serviço por nome ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Servico> findByNomeAndIdNot(String nome, Long id) {
        return find("nome = ?1 and id != ?2", nome, id).firstResultOptional();
    }
    
    /**
     * Busca serviços ordenados por nome
     */
    public List<Servico> findOrderByNome() {
        return find("order by nome").list();
    }
    
    /**
     * Busca serviços ordenados por valor base (menor para maior)
     */
    public List<Servico> findOrderByValorBaseAsc() {
        return find("order by valorBase asc").list();
    }
    
    /**
     * Busca serviços ordenados por valor base (maior para menor)
     */
    public List<Servico> findOrderByValorBaseDesc() {
        return find("order by valorBase desc").list();
    }
    
    /**
     * Busca serviços ordenados por categoria e nome
     */
    public List<Servico> findOrderByCategoriaAscNomeAsc() {
        return find("order by categoria asc, nome asc").list();
    }
    
    /**
     * Conta serviços por categoria
     */
    public long countByCategoria(Servico.CategoriaServico categoria) {
        return count("categoria", categoria);
    }
    
    /**
     * Conta serviços ativos
     */
    public long countByAtivoTrue() {
        return count("ativo", true);
    }
} 