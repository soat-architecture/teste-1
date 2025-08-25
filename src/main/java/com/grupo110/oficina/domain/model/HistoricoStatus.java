package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_status")
public class HistoricoStatus  {

    @Id private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    @NotNull(message = "Ordem de serviço é obrigatória")
    private OrdemServico ordemServico;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private OrdemServico.StatusOrdemServico status;
    
    @Column(name = "data_alteracao", nullable = false)
    @NotNull(message = "Data de alteração é obrigatória")
    private LocalDateTime dataAlteracao;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "usuario_alteracao")
    private String usuarioAlteracao;
    
    // Construtores
    public HistoricoStatus() {}
    
    public HistoricoStatus(OrdemServico ordemServico, OrdemServico.StatusOrdemServico status) {
        this.ordemServico = ordemServico;
        this.status = status;
        this.dataAlteracao = LocalDateTime.now();
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
    
    public OrdemServico.StatusOrdemServico getStatus() {
        return status;
    }
    
    public void setStatus(OrdemServico.StatusOrdemServico status) {
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
    
    @Override
    public String toString() {
        return "HistoricoStatus{" +
                "id=" + id +
                ", status=" + status +
                ", dataAlteracao=" + dataAlteracao +
                ", usuarioAlteracao='" + usuarioAlteracao + '\'' +
                '}';
    }
} 