package com.grupo110.oficina.application.dto;

import com.grupo110.oficina.domain.model.HistoricoStatus;
import com.grupo110.oficina.domain.model.OrdemServico;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class HistoricoStatusDTO {
    
    private Long id;
    
    @NotNull(message = "Status é obrigatório")
    private String status;
    
    @NotNull(message = "Data de alteração é obrigatória")
    private LocalDateTime dataAlteracao;
    
    private String observacoes;
    private String usuarioAlteracao;
    
    // Relacionamentos
    private Long ordemServicoId;
    
    // Construtores
    public HistoricoStatusDTO() {}
    
    public HistoricoStatusDTO(HistoricoStatus historicoStatus) {
        this.id = historicoStatus.getId();
        this.status = historicoStatus.getStatus().name();
        this.dataAlteracao = historicoStatus.getDataAlteracao();
        this.observacoes = historicoStatus.getObservacoes();
        this.usuarioAlteracao = historicoStatus.getUsuarioAlteracao();
        
        if (historicoStatus.getOrdemServico() != null) {
            this.ordemServicoId = historicoStatus.getOrdemServico().getId();
        }
    }
    
    // Método para converter DTO para entidade
    public HistoricoStatus toEntity() {
        HistoricoStatus historicoStatus = new HistoricoStatus();
        historicoStatus.setId(this.id);
        if (this.status != null) {
            historicoStatus.setStatus(OrdemServico.StatusOrdemServico.valueOf(this.status));
        }
        historicoStatus.setDataAlteracao(this.dataAlteracao);
        historicoStatus.setObservacoes(this.observacoes);
        historicoStatus.setUsuarioAlteracao(this.usuarioAlteracao);
        return historicoStatus;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }
    
    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public String getUsuarioAlteracao() {
        return usuarioAlteracao;
    }
    
    public void setUsuarioAlteracao(String usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }
    
    public Long getOrdemServicoId() {
        return ordemServicoId;
    }
    
    public void setOrdemServicoId(Long ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }
    
    @Override
    public String toString() {
        return "HistoricoStatusDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", dataAlteracao=" + dataAlteracao +
                ", usuarioAlteracao='" + usuarioAlteracao + '\'' +
                '}';
    }
} 