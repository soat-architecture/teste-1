package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.ItemServico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ItemServicoRepository implements PanacheRepository<ItemServico> {

    /**
     * Busca itens de serviço por ordem de serviço
     */
    public List<ItemServico> findByOrdemServicoId(Long ordemServicoId) {
        return find("ordemServico.id", ordemServicoId).list();
    }
    
    /**
     * Busca itens de serviço por serviço
     */
    public List<ItemServico> findByServicoId(Long servicoId) {
        return find("servico.id", servicoId).list();
    }
    
    /**
     * Busca itens de serviço por ordem de serviço e serviço
     */
    public List<ItemServico> findByOrdemServicoIdAndServicoId(Long ordemServicoId, Long servicoId) {
        return find("ordemServico.id = ?1 and servico.id = ?2", ordemServicoId, servicoId).list();
    }
    
    /**
     * Busca itens de serviço por valor base maior que
     */
    public List<ItemServico> findByValorBaseGreaterThan(BigDecimal valor) {
        return find("valorBase > ?1", valor).list();
    }
    
    /**
     * Busca itens de serviço por valor base entre
     */
    public List<ItemServico> findByValorBaseBetween(BigDecimal valorMin, BigDecimal valorMax) {
        return find("valorBase between ?1 and ?2", valorMin, valorMax).list();
    }
    
    /**
     * Busca itens de serviço por valor total maior que
     */
    public List<ItemServico> findByValorTotalGreaterThan(BigDecimal valor) {
        return find("valorTotal > ?1", valor).list();
    }
    
    /**
     * Busca itens de serviço por valor total entre
     */
    public List<ItemServico> findByValorTotalBetween(BigDecimal valorMin, BigDecimal valorMax) {
        return find("valorTotal between ?1 and ?2", valorMin, valorMax).list();
    }
    
    /**
     * Busca itens de serviço por tempo de execução maior que
     */
    public List<ItemServico> findByTempoExecucaoGreaterThan(Integer tempoMinutos) {
        return find("tempoExecucao > ?1", tempoMinutos).list();
    }
    
    /**
     * Busca itens de serviço por tempo de execução entre
     */
    public List<ItemServico> findByTempoExecucaoBetween(Integer tempoMin, Integer tempoMax) {
        return find("tempoExecucao between ?1 and ?2", tempoMin, tempoMax).list();
    }
    
    /**
     * Busca itens de serviço por ordem de serviço ordenados por valor total (maior para menor)
     */
    public List<ItemServico> findByOrdemServicoIdOrderByValorTotalDesc(Long ordemServicoId) {
        return find("ordemServico.id = ?1 order by valorTotal desc", ordemServicoId).list();
    }
    
    /**
     * Busca itens de serviço por serviço ordenados por data de criação (mais recentes primeiro)
     */
    public List<ItemServico> findByServicoIdOrderByDataCriacaoDesc(Long servicoId) {
        return find("servico.id = ?1 order by dataCriacao desc", servicoId).list();
    }
    
    /**
     * Conta itens de serviço por ordem de serviço
     */
    public long countByOrdemServicoId(Long ordemServicoId) {
        return count("ordemServico.id", ordemServicoId);
    }
    
    /**
     * Conta itens de serviço por serviço
     */
    public long countByServicoId(Long servicoId) {
        return count("servico.id", servicoId);
    }
    
    /**
     * Conta itens de serviço por valor base maior que
     */
    public long countByValorBaseGreaterThan(BigDecimal valor) {
        return count("valorBase > ?1", valor);
    }
    
    /**
     * Conta itens de serviço por valor total maior que
     */
    public long countByValorTotalGreaterThan(BigDecimal valor) {
        return count("valorTotal > ?1", valor);
    }
    
    /**
     * Busca itens de serviço com desconto aplicado
     */
    public List<ItemServico> findItensComDesconto() {
        return find("desconto > 0").list();
    }
    
    /**
     * Busca itens de serviço por percentual de desconto maior que
     */
    public List<ItemServico> findByPercentualDescontoGreaterThan(BigDecimal percentual) {
        return find("percentualDesconto > ?1", percentual).list();
    }
    
    /**
     * Busca itens de serviço por ordem de serviço e valor total maior que
     */
    public List<ItemServico> findByOrdemServicoIdAndValorTotalGreaterThan(Long ordemServicoId, BigDecimal valor) {
        return find("ordemServico.id = ?1 and valorTotal > ?2", ordemServicoId, valor).list();
    }
    
    /**
     * Busca itens de serviço por serviço e tempo de execução maior que
     */
    public List<ItemServico> findByServicoIdAndTempoExecucaoGreaterThan(Long servicoId, Integer tempoMinutos) {
        return find("servico.id = ?1 and tempoExecucao > ?2", servicoId, tempoMinutos).list();
    }
} 