package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_peca")
public class ItemPeca  {

    @Id private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    @NotNull(message = "Ordem de serviço é obrigatória")
    private OrdemServico ordemServico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peca_id", nullable = false)
    @NotNull(message = "Peça é obrigatória")
    private Peca peca;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    
    @NotNull(message = "Valor unitário é obrigatório")
    @Positive(message = "Valor unitário deve ser positivo")
    @Column(name = "valor_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorUnitario;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    // Construtores
    public ItemPeca() {}
    
    public ItemPeca(OrdemServico ordemServico, Peca peca, Integer quantidade, BigDecimal valorUnitario) {
        this.ordemServico = ordemServico;
        this.peca = peca;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }
    
    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }
    
    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
    
    public Peca getPeca() {
        return peca;
    }
    
    public void setPeca(Peca peca) {
        this.peca = peca;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }
    
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    // Métodos de negócio
    public BigDecimal getValorTotal() {
        return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
    
    @Override
    public String toString() {
        return "ItemPeca{" +
                "id=" + id +
                ", peca=" + (peca != null ? peca.getNome() : "null") +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                '}';
    }
} 