package com.grupo110.oficina.application.dto;

import com.grupo110.oficina.domain.model.ItemServico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ItemServicoDTO {
    
    private Long id;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade;
    
    @NotNull(message = "Valor unitário é obrigatório")
    @Positive(message = "Valor unitário deve ser positivo")
    private BigDecimal valorUnitario;
    
    private String observacoes;
    
    // Relacionamentos
    private Long ordemServicoId;
    private Long servicoId;
    private String servicoNome;
    private String servicoCategoria;
    
    // Construtores
    public ItemServicoDTO() {}
    
    public ItemServicoDTO(ItemServico itemServico) {
        this.id = itemServico.getId();
        this.quantidade = itemServico.getQuantidade();
        this.valorUnitario = itemServico.getValorUnitario();
        this.observacoes = itemServico.getObservacoes();
        
        if (itemServico.getOrdemServico() != null) {
            this.ordemServicoId = itemServico.getOrdemServico().getId();
        }
        
        if (itemServico.getServico() != null) {
            this.servicoId = itemServico.getServico().getId();
            this.servicoNome = itemServico.getServico().getNome();
            this.servicoCategoria = itemServico.getServico().getCategoria() != null ? 
                itemServico.getServico().getCategoria().name() : null;
        }
    }
    
    // Método para converter DTO para entidade
    public ItemServico toEntity() {
        ItemServico itemServico = new ItemServico();
        itemServico.setId(this.id);
        itemServico.setQuantidade(this.quantidade);
        itemServico.setValorUnitario(this.valorUnitario);
        itemServico.setObservacoes(this.observacoes);
        return itemServico;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public Long getOrdemServicoId() {
        return ordemServicoId;
    }
    
    public void setOrdemServicoId(Long ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }
    
    public Long getServicoId() {
        return servicoId;
    }
    
    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }
    
    public String getServicoNome() {
        return servicoNome;
    }
    
    public void setServicoNome(String servicoNome) {
        this.servicoNome = servicoNome;
    }
    
    public String getServicoCategoria() {
        return servicoCategoria;
    }
    
    public void setServicoCategoria(String servicoCategoria) {
        this.servicoCategoria = servicoCategoria;
    }
    
    // Método para calcular valor total
    public BigDecimal getValorTotal() {
        if (quantidade != null && valorUnitario != null) {
            return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public String toString() {
        return "ItemServicoDTO{" +
                "id=" + id +
                ", servicoNome='" + servicoNome + '\'' +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                '}';
    }
} 