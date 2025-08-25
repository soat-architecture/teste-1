package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.ItemPeca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ItemPecaRepository implements PanacheRepository<ItemPeca> {

    /**
     * Busca itens de peça por ordem de serviço
     */
    public List<ItemPeca> findByOrdemServicoId(Long ordemServicoId) {
        return find("ordemServico.id", ordemServicoId).list();
    }
    
    /**
     * Busca itens de peça por peça
     */
    public List<ItemPeca> findByPecaId(Long pecaId) {
        return find("peca.id", pecaId).list();
    }
    
    /**
     * Busca itens de peça por ordem de serviço e peça
     */
    public List<ItemPeca> findByOrdemServicoIdAndPecaId(Long ordemServicoId, Long pecaId) {
        return find("ordemServico.id = ?1 and peca.id = ?2", ordemServicoId, pecaId).list();
    }
    
    /**
     * Busca itens de peça por quantidade maior que
     */
    public List<ItemPeca> findByQuantidadeGreaterThan(Integer quantidade) {
        return find("quantidade > ?1", quantidade).list();
    }
    
    /**
     * Busca itens de peça por quantidade entre
     */
    public List<ItemPeca> findByQuantidadeBetween(Integer quantidadeMin, Integer quantidadeMax) {
        return find("quantidade between ?1 and ?2", quantidadeMin, quantidadeMax).list();
    }
    
    /**
     * Busca itens de peça por valor unitário maior que
     */
    public List<ItemPeca> findByValorUnitarioGreaterThan(BigDecimal valor) {
        return find("valorUnitario > ?1", valor).list();
    }
    
    /**
     * Busca itens de peça por valor unitário entre
     */
    public List<ItemPeca> findByValorUnitarioBetween(BigDecimal valorMin, BigDecimal valorMax) {
        return find("valorUnitario between ?1 and ?2", valorMin, valorMax).list();
    }
    
    /**
     * Busca itens de peça por valor total maior que
     */
    public List<ItemPeca> findByValorTotalGreaterThan(BigDecimal valor) {
        return find("valorTotal > ?1", valor).list();
    }
    
    /**
     * Busca itens de peça por valor total entre
     */
    public List<ItemPeca> findByValorTotalBetween(BigDecimal valorMin, BigDecimal valorMax) {
        return find("valorTotal between ?1 and ?2", valorMin, valorMax).list();
    }
    
    /**
     * Busca itens de peça por ordem de serviço ordenados por valor total (maior para menor)
     */
    public List<ItemPeca> findByOrdemServicoIdOrderByValorTotalDesc(Long ordemServicoId) {
        return find("ordemServico.id = ?1 order by valorTotal desc", ordemServicoId).list();
    }
    
    /**
     * Busca itens de peça por peça ordenados por data de criação (mais recentes primeiro)
     */
    public List<ItemPeca> findByPecaIdOrderByDataCriacaoDesc(Long pecaId) {
        return find("peca.id = ?1 order by dataCriacao desc", pecaId).list();
    }
    
    /**
     * Conta itens de peça por ordem de serviço
     */
    public long countByOrdemServicoId(Long ordemServicoId) {
        return count("ordemServico.id", ordemServicoId);
    }
    
    /**
     * Conta itens de peça por peça
     */
    public long countByPecaId(Long pecaId) {
        return count("peca.id", pecaId);
    }
    
    /**
     * Conta itens de peça por quantidade maior que
     */
    public long countByQuantidadeGreaterThan(Integer quantidade) {
        return count("quantidade > ?1", quantidade);
    }
    
    /**
     * Conta itens de peça por valor total maior que
     */
    public long countByValorTotalGreaterThan(BigDecimal valor) {
        return count("valorTotal > ?1", valor);
    }
    
    /**
     * Busca itens de peça com desconto aplicado
     */
    public List<ItemPeca> findItensComDesconto() {
        return find("desconto > 0").list();
    }
    
    /**
     * Busca itens de peça por percentual de desconto maior que
     */
    public List<ItemPeca> findByPercentualDescontoGreaterThan(BigDecimal percentual) {
        return find("percentualDesconto > ?1", percentual).list();
    }
    
    /**
     * Busca itens de peça por ordem de serviço e valor total maior que
     */
    public List<ItemPeca> findByOrdemServicoIdAndValorTotalGreaterThan(Long ordemServicoId, BigDecimal valor) {
        return find("ordemServico.id = ?1 and valorTotal > ?2", ordemServicoId, valor).list();
    }
    
    /**
     * Busca itens de peça por peça e quantidade maior que
     */
    public List<ItemPeca> findByPecaIdAndQuantidadeGreaterThan(Long pecaId, Integer quantidade) {
        return find("peca.id = ?1 and quantidade > ?2", pecaId, quantidade).list();
    }
} 