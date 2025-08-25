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
@Table(name = "pecas")
public class Peca  {

    @Id private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "codigo", unique = true)
    private String codigo;
    
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
    
    @Column(name = "fabricante")
    private String fabricante;
    
    @Column(name = "marca_veiculo")
    private String marcaVeiculo;
    
    @Column(name = "modelo_veiculo")
    private String modeloVeiculo;
    
    @Column(name = "ano_inicio")
    private Integer anoInicio;
    
    @Column(name = "ano_fim")
    private Integer anoFim;
    
    @NotNull(message = "Preço de custo é obrigatório")
    @Positive(message = "Preço de custo deve ser positivo")
    @Column(name = "preco_custo", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoCusto;
    
    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "Preço de venda deve ser positivo")
    @Column(name = "preco_venda", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoVenda;
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;
    
    @Column(name = "quantidade_minima")
    private Integer quantidadeMinima;
    
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    
    @Column(name = "localizacao_estoque")
    private String localizacaoEstoque;
    
    @Column(name = "categoria")
    @Enumerated(EnumType.STRING)
    private CategoriaPeca categoria;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @OneToMany(mappedBy = "peca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPeca> itensPeca = new ArrayList<>();
    
    public enum CategoriaPeca {
        MOTOR("Motor"),
        TRANSMISSAO("Transmissão"),
        SUSPENSAO("Suspensão"),
        FREIOS("Freios"),
        ELETRICA("Elétrica"),
        FILTROS("Filtros"),
        LUBRIFICANTES("Lubrificantes"),
        PNEUS("Pneus"),
        ACESSORIOS("Acessórios"),
        OUTROS("Outros");
        
        private final String descricao;
        
        CategoriaPeca(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Peca() {
        this.dataCadastro = LocalDateTime.now();
        this.quantidadeEstoque = 0;
    }
    
    public Peca(String nome, String codigo, BigDecimal precoCusto, BigDecimal precoVenda) {
        this();
        this.nome = nome;
        this.codigo = codigo;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
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
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getFabricante() {
        return fabricante;
    }
    
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    
    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }
    
    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }
    
    public String getModeloVeiculo() {
        return modeloVeiculo;
    }
    
    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }
    
    public Integer getAnoInicio() {
        return anoInicio;
    }
    
    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }
    
    public Integer getAnoFim() {
        return anoFim;
    }
    
    public void setAnoFim(Integer anoFim) {
        this.anoFim = anoFim;
    }
    
    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }
    
    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }
    
    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }
    
    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }
    
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
    
    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }
    
    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
    
    public String getUnidadeMedida() {
        return unidadeMedida;
    }
    
    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    
    public String getLocalizacaoEstoque() {
        return localizacaoEstoque;
    }
    
    public void setLocalizacaoEstoque(String localizacaoEstoque) {
        this.localizacaoEstoque = localizacaoEstoque;
    }
    
    public CategoriaPeca getCategoria() {
        return categoria;
    }
    
    public void setCategoria(CategoriaPeca categoria) {
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
    
    public List<ItemPeca> getItensPeca() {
        return itensPeca;
    }
    
    public void setItensPeca(List<ItemPeca> itensPeca) {
        this.itensPeca = itensPeca;
    }
    
    // Métodos de negócio
    public void adicionarItemPeca(ItemPeca itemPeca) {
        itemPeca.setPeca(this);
        this.itensPeca.add(itemPeca);
    }
    
    public void removerItemPeca(ItemPeca itemPeca) {
        this.itensPeca.remove(itemPeca);
        itemPeca.setPeca(null);
    }
    
    public void adicionarEstoque(Integer quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
        }
    }
    
    public boolean removerEstoque(Integer quantidade) {
        if (quantidade > 0 && this.quantidadeEstoque >= quantidade) {
            this.quantidadeEstoque -= quantidade;
            return true;
        }
        return false;
    }
    
    public boolean temEstoqueSuficiente(Integer quantidade) {
        return this.quantidadeEstoque >= quantidade;
    }
    
    public boolean estoqueBaixo() {
        return this.quantidadeMinima != null && this.quantidadeEstoque <= this.quantidadeMinima;
    }
    
    public BigDecimal calcularMargemLucro() {
        if (precoCusto != null && precoCusto.compareTo(BigDecimal.ZERO) > 0) {
            return precoVenda.subtract(precoCusto);
        }
        return BigDecimal.ZERO;
    }
    
    public BigDecimal calcularPercentualLucro() {
        if (precoCusto != null && precoCusto.compareTo(BigDecimal.ZERO) > 0) {
            return precoVenda.subtract(precoCusto)
                    .divide(precoCusto, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Peca{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", categoria=" + categoria +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", precoVenda=" + precoVenda +
                ", ativo=" + ativo +
                '}';
    }
} 