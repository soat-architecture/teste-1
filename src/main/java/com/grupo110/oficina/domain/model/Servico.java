package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicos")
public class Servico  {

    @Id private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
    
    @NotNull(message = "Valor base é obrigatório")
    @Positive(message = "Valor base deve ser positivo")
    @Column(name = "valor_base", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorBase;
    
    @Column(name = "tempo_medio_execucao")
    private Integer tempoMedioExecucao; // em minutos
    
    @Column(name = "categoria")
    @Enumerated(EnumType.STRING)
    private CategoriaServico categoria;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemServico> itensServico = new ArrayList<>();
    
    public enum CategoriaServico {
        MECANICA("Mecânica"),
        ELETRICA("Elétrica"),
        SUSPENSAO("Suspensão"),
        FREIOS("Freios"),
        MOTOR("Motor"),
        TRANSMISSAO("Transmissão"),
        AR_CONDICIONADO("Ar Condicionado"),
        PINTURA("Pintura"),
        FUNILARIA("Funilaria"),
        OUTROS("Outros");
        
        private final String descricao;
        
        CategoriaServico(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Servico() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Servico(String nome, String descricao, BigDecimal valorBase, CategoriaServico categoria) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.categoria = categoria;
    }
    
    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getValorBase() {
        return valorBase;
    }
    
    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }
    
    public Integer getTempoMedioExecucao() {
        return tempoMedioExecucao;
    }
    
    public void setTempoMedioExecucao(Integer tempoMedioExecucao) {
        this.tempoMedioExecucao = tempoMedioExecucao;
    }
    
    public CategoriaServico getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaServico categoria) {
        this.categoria = categoria;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }
    
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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
    
    // Métodos de negócio
    public void adicionarItemServico(ItemServico itemServico) {
        itemServico.setServico(this);
        this.itensServico.add(itemServico);
    }
    
    public void removerItemServico(ItemServico itemServico) {
        this.itensServico.remove(itemServico);
        itemServico.setServico(null);
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria=" + categoria +
                ", valorBase=" + valorBase +
                ", ativo=" + ativo +
                '}';
    }
} 