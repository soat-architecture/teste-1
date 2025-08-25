package com.grupo110.oficina.application.dto;

import com.grupo110.oficina.domain.model.ItemPeca;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ItemPecaDTO {
    
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
    private Long pecaId;
    private String pecaNome;
    private String pecaCodigo;
    private String pecaCategoria;
    
    // Construtores
    public ItemPecaDTO() {}
    
    public ItemPecaDTO(ItemPeca itemPeca) {
        this.id = itemPeca.getId();
        this.quantidade = itemPeca.getQuantidade();
        this.valorUnitario = itemPeca.getValorUnitario();
        this.observacoes = itemPeca.getObservacoes();
        
        if (itemPeca.getOrdemServico() != null) {
            this.ordemServicoId = itemPeca.getOrdemServico().getId();
        }
        
        if (itemPeca.getPeca() != null) {
            this.pecaId = itemPeca.getPeca().getId();
            this.pecaNome = itemPeca.getPeca().getNome();
            this.pecaCodigo = itemPeca.getPeca().getCodigo();
            this.pecaCategoria = itemPeca.getPeca().getCategoria() != null ? 
                itemPeca.getPeca().getCategoria().name() : null;
        }
    }
    
    // Método para converter DTO para entidade
    public ItemPeca toEntity() {
        ItemPeca itemPeca = new ItemPeca();
        itemPeca.setId(this.id);
        itemPeca.setQuantidade(this.quantidade);
        itemPeca.setValorUnitario(this.valorUnitario);
        itemPeca.setObservacoes(this.observacoes);
        return itemPeca;
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
    
    public Long getPecaId() {
        return pecaId;
    }
    
    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }
    
    public String getPecaNome() {
        return pecaNome;
    }
    
    public void setPecaNome(String pecaNome) {
        this.pecaNome = pecaNome;
    }
    
    public String getPecaCodigo() {
        return pecaCodigo;
    }
    
    public void setPecaCodigo(String pecaCodigo) {
        this.pecaCodigo = pecaCodigo;
    }
    
    public String getPecaCategoria() {
        return pecaCategoria;
    }
    
    public void setPecaCategoria(String pecaCategoria) {
        this.pecaCategoria = pecaCategoria;
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
        return "ItemPecaDTO{" +
                "id=" + id +
                ", pecaNome='" + pecaNome + '\'' +
                ", quantidade=" + quantidade +
                ", valorUnitario=" + valorUnitario +
                '}';
    }
} 