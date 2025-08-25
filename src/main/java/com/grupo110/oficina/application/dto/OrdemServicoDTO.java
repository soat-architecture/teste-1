package com.grupo110.oficina.application.dto;

import com.grupo110.oficina.domain.model.OrdemServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemServicoDTO {
    
    private Long id;
    private String numeroOS;
    
    @NotNull(message = "Status é obrigatório")
    private String status;
    
    @NotBlank(message = "Descrição do problema é obrigatória")
    private String descricaoProblema;
    
    private String diagnostico;
    private String observacoes;
    private BigDecimal valorTotal;
    private BigDecimal valorMaoObra;
    private BigDecimal valorPecas;
    private LocalDateTime prazoEntrega;
    private LocalDateTime dataRecebimento;
    private LocalDateTime dataInicioExecucao;
    private LocalDateTime dataFinalizacao;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    
    // Relacionamentos
    private Long clienteId;
    private String clienteNome;
    private Long veiculoId;
    private String veiculoPlaca;
    private String veiculoModelo;
    
    // Listas de itens
    private List<ItemServicoDTO> itensServico;
    private List<ItemPecaDTO> itensPeca;
    private List<HistoricoStatusDTO> historicoStatus;
    
    // Construtores
    public OrdemServicoDTO() {}
    
    public OrdemServicoDTO(OrdemServico ordemServico) {
        this.id = ordemServico.getId();
        this.numeroOS = ordemServico.getNumeroOS();
        this.status = ordemServico.getStatus().name();
        this.descricaoProblema = ordemServico.getDescricaoProblema();
        this.diagnostico = ordemServico.getDiagnostico();
        this.observacoes = ordemServico.getObservacoes();
        this.valorTotal = ordemServico.getValorTotal();
        this.valorMaoObra = ordemServico.getValorMaoObra();
        this.valorPecas = ordemServico.getValorPecas();
        this.prazoEntrega = ordemServico.getPrazoEntrega();
        this.dataRecebimento = ordemServico.getDataRecebimento();
        this.dataInicioExecucao = ordemServico.getDataInicioExecucao();
        this.dataFinalizacao = ordemServico.getDataFinalizacao();
        this.dataEntrega = ordemServico.getDataEntrega();
        this.dataCadastro = ordemServico.getDataCadastro();
        this.dataAtualizacao = ordemServico.getDataAtualizacao();
        
        if (ordemServico.getCliente() != null) {
            this.clienteId = ordemServico.getCliente().getId();
            this.clienteNome = ordemServico.getCliente().getNome();
        }
        
        if (ordemServico.getVeiculo() != null) {
            this.veiculoId = ordemServico.getVeiculo().getId();
            this.veiculoPlaca = ordemServico.getVeiculo().getPlaca();
            this.veiculoModelo = ordemServico.getVeiculo().getModelo();
        }
        
        // Converter itens de serviço se disponível
        if (ordemServico.getItensServico() != null && !ordemServico.getItensServico().isEmpty()) {
            this.itensServico = ordemServico.getItensServico().stream()
                    .map(ItemServicoDTO::new)
                    .collect(Collectors.toList());
        }
        
        // Converter itens de peça se disponível
        if (ordemServico.getItensPeca() != null && !ordemServico.getItensPeca().isEmpty()) {
            this.itensPeca = ordemServico.getItensPeca().stream()
                    .map(ItemPecaDTO::new)
                    .collect(Collectors.toList());
        }
        
        // Converter histórico de status se disponível
        if (ordemServico.getHistoricoStatus() != null && !ordemServico.getHistoricoStatus().isEmpty()) {
            this.historicoStatus = ordemServico.getHistoricoStatus().stream()
                    .map(HistoricoStatusDTO::new)
                    .collect(Collectors.toList());
        }
    }
    
    // Método para converter DTO para entidade
    public OrdemServico toEntity() {
        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setId(this.id);
        ordemServico.setNumeroOS(this.numeroOS);
        if (this.status != null) {
            ordemServico.setStatus(OrdemServico.StatusOrdemServico.valueOf(this.status));
        }
        ordemServico.setDescricaoProblema(this.descricaoProblema);
        ordemServico.setDiagnostico(this.diagnostico);
        ordemServico.setObservacoes(this.observacoes);
        ordemServico.setValorTotal(this.valorTotal);
        ordemServico.setValorMaoObra(this.valorMaoObra);
        ordemServico.setValorPecas(this.valorPecas);
        ordemServico.setPrazoEntrega(this.prazoEntrega);
        ordemServico.setDataRecebimento(this.dataRecebimento);
        ordemServico.setDataInicioExecucao(this.dataInicioExecucao);
        ordemServico.setDataFinalizacao(this.dataFinalizacao);
        ordemServico.setDataEntrega(this.dataEntrega);
        ordemServico.setDataCadastro(this.dataCadastro);
        ordemServico.setDataAtualizacao(this.dataAtualizacao);
        return ordemServico;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroOS() {
        return numeroOS;
    }
    
    public void setNumeroOS(String numeroOS) {
        this.numeroOS = numeroOS;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescricaoProblema() {
        return descricaoProblema;
    }
    
    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }
    
    public String getDiagnostico() {
        return diagnostico;
    }
    
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public BigDecimal getValorMaoObra() {
        return valorMaoObra;
    }
    
    public void setValorMaoObra(BigDecimal valorMaoObra) {
        this.valorMaoObra = valorMaoObra;
    }
    
    public BigDecimal getValorPecas() {
        return valorPecas;
    }
    
    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }
    
    public LocalDateTime getPrazoEntrega() {
        return prazoEntrega;
    }
    
    public void setPrazoEntrega(LocalDateTime prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
    
    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }
    
    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }
    
    public LocalDateTime getDataInicioExecucao() {
        return dataInicioExecucao;
    }
    
    public void setDataInicioExecucao(LocalDateTime dataInicioExecucao) {
        this.dataInicioExecucao = dataInicioExecucao;
    }
    
    public LocalDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }
    
    public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }
    
    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }
    
    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getClienteNome() {
        return clienteNome;
    }
    
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    
    public Long getVeiculoId() {
        return veiculoId;
    }
    
    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }
    
    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }
    
    public void setVeiculoPlaca(String veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }
    
    public String getVeiculoModelo() {
        return veiculoModelo;
    }
    
    public void setVeiculoModelo(String veiculoModelo) {
        this.veiculoModelo = veiculoModelo;
    }
    
    public List<ItemServicoDTO> getItensServico() {
        return itensServico;
    }
    
    public void setItensServico(List<ItemServicoDTO> itensServico) {
        this.itensServico = itensServico;
    }
    
    public List<ItemPecaDTO> getItensPeca() {
        return itensPeca;
    }
    
    public void setItensPeca(List<ItemPecaDTO> itensPeca) {
        this.itensPeca = itensPeca;
    }
    
    public List<HistoricoStatusDTO> getHistoricoStatus() {
        return historicoStatus;
    }
    
    public void setHistoricoStatus(List<HistoricoStatusDTO> historicoStatus) {
        this.historicoStatus = historicoStatus;
    }
    
    @Override
    public String toString() {
        return "OrdemServicoDTO{" +
                "id=" + id +
                ", numeroOS='" + numeroOS + '\'' +
                ", status='" + status + '\'' +
                ", clienteNome='" + clienteNome + '\'' +
                ", veiculoPlaca='" + veiculoPlaca + '\'' +
                '}';
    }
} 