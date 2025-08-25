package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordens_servico")
public class OrdemServico  {
    @Id private Long id;
    
    @Column(name = "numero_os", nullable = false, unique = true)
    private String numeroOS;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusOrdemServico status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    @NotNull(message = "Veículo é obrigatório")
    private Veiculo veiculo;
    
    @Column(name = "descricao_problema", columnDefinition = "TEXT")
    @NotBlank(message = "Descrição do problema é obrigatória")
    private String descricaoProblema;
    
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @Column(name = "valor_mao_obra", precision = 10, scale = 2)
    private BigDecimal valorMaoObra;
    
    @Column(name = "valor_pecas", precision = 10, scale = 2)
    private BigDecimal valorPecas;
    
    @Column(name = "prazo_entrega")
    private LocalDateTime prazoEntrega;
    
    @Column(name = "data_recebimento", nullable = false)
    private LocalDateTime dataRecebimento;
    
    @Column(name = "data_inicio_execucao")
    private LocalDateTime dataInicioExecucao;
    
    @Column(name = "data_finalizacao")
    private LocalDateTime dataFinalizacao;
    
    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemServico> itensServico = new ArrayList<>();
    
    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPeca> itensPeca = new ArrayList<>();
    
    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoricoStatus> historicoStatus = new ArrayList<>();
    
    public enum StatusOrdemServico {
        RECEBIDA("Recebida"),
        EM_DIAGNOSTICO("Em Diagnóstico"),
        AGUARDANDO_APROVACAO("Aguardando Aprovação"),
        EM_EXECUCAO("Em Execução"),
        FINALIZADA("Finalizada"),
        ENTREGUE("Entregue");
        
        private final String descricao;
        
        StatusOrdemServico(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public OrdemServico() {
        this.dataCadastro = LocalDateTime.now();
        this.dataRecebimento = LocalDateTime.now();
        this.status = StatusOrdemServico.RECEBIDA;
        this.numeroOS = gerarNumeroOS();
    }
    
    public OrdemServico(Cliente cliente, Veiculo veiculo, String descricaoProblema) {
        this();
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.descricaoProblema = descricaoProblema;
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
    
    public StatusOrdemServico getStatus() {
        return status;
    }
    
    public void setStatus(StatusOrdemServico status) {
        this.status = status;
        adicionarHistoricoStatus(status);
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Veiculo getVeiculo() {
        return veiculo;
    }
    
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
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
    
    public List<ItemServico> getItensServico() {
        return itensServico;
    }
    
    public void setItensServico(List<ItemServico> itensServico) {
        this.itensServico = itensServico;
    }
    
    public List<ItemPeca> getItensPeca() {
        return itensPeca;
    }
    
    public void setItensPeca(List<ItemPeca> itensPeca) {
        this.itensPeca = itensPeca;
    }
    
    public List<HistoricoStatus> getHistoricoStatus() {
        return historicoStatus;
    }
    
    public void setHistoricoStatus(List<HistoricoStatus> historicoStatus) {
        this.historicoStatus = historicoStatus;
    }
    
    // Métodos de negócio
    public void adicionarItemServico(ItemServico itemServico) {
        itemServico.setOrdemServico(this);
        this.itensServico.add(itemServico);
        calcularValores();
    }
    
    public void removerItemServico(ItemServico itemServico) {
        this.itensServico.remove(itemServico);
        itemServico.setOrdemServico(null);
        calcularValores();
    }
    
    public void adicionarItemPeca(ItemPeca itemPeca) {
        itemPeca.setOrdemServico(this);
        this.itensPeca.add(itemPeca);
        calcularValores();
    }
    
    public void removerItemPeca(ItemPeca itemPeca) {
        this.itensPeca.remove(itemPeca);
        itemPeca.setOrdemServico(null);
        calcularValores();
    }
    
    public void adicionarHistoricoStatus(StatusOrdemServico novoStatus) {
        HistoricoStatus historico = new HistoricoStatus();
        historico.setOrdemServico(this);
        historico.setStatus(novoStatus);
        historico.setDataAlteracao(LocalDateTime.now());
        this.historicoStatus.add(historico);
    }
    
    public void calcularValores() {
        // Calcular valor das peças
        this.valorPecas = this.itensPeca.stream()
                .map(item -> item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calcular valor da mão de obra
        this.valorMaoObra = this.itensServico.stream()
                .map(item -> item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calcular valor total
        this.valorTotal = this.valorPecas.add(this.valorMaoObra);
    }
    
    public void iniciarDiagnostico() {
        this.status = StatusOrdemServico.EM_DIAGNOSTICO;
    }
    
    public void aguardarAprovacao() {
        this.status = StatusOrdemServico.AGUARDANDO_APROVACAO;
    }
    
    public void iniciarExecucao() {
        this.status = StatusOrdemServico.EM_EXECUCAO;
        this.dataInicioExecucao = LocalDateTime.now();
    }
    
    public void finalizar() {
        this.status = StatusOrdemServico.FINALIZADA;
        this.dataFinalizacao = LocalDateTime.now();
    }
    
    public void entregar() {
        this.status = StatusOrdemServico.ENTREGUE;
        this.dataEntrega = LocalDateTime.now();
    }
    
    private String gerarNumeroOS() {
        return "OS" + System.currentTimeMillis();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "OrdemServico{" +
                "id=" + id +
                ", numeroOS='" + numeroOS + '\'' +
                ", status=" + status +
                ", cliente=" + (cliente != null ? cliente.getId() : "null") +
                ", veiculo=" + (veiculo != null ? veiculo.getId() : "null") +
                '}';
    }
} 