package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculos")
public class Veiculo  {

    @Id private Long id;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Placa deve estar no formato Mercosul (ABC1D23)")
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;
    
    @NotBlank(message = "Marca é obrigatória")
    @Size(min = 2, max = 50, message = "Marca deve ter entre 2 e 50 caracteres")
    @Column(name = "marca", nullable = false)
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Size(min = 2, max = 100, message = "Modelo deve ter entre 2 e 100 caracteres")
    @Column(name = "modelo", nullable = false)
    private String modelo;
    
    @NotNull(message = "Ano é obrigatório")
    @Column(name = "ano", nullable = false)
    private Integer ano;
    
    @Column(name = "cor")
    private String cor;
    
    @Column(name = "chassi", length = 17)
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]{17}$", message = "Chassi deve ter 17 caracteres alfanuméricos válidos")
    private String chassi;
    
    @Column(name = "renavam", length = 11)
    @Pattern(regexp = "^\\d{11}$", message = "RENAVAM deve ter 11 dígitos")
    private String renavam;
    
    @Column(name = "quilometragem")
    private Long quilometragem;
    
    @Column(name = "combustivel")
    @Enumerated(EnumType.STRING)
    private TipoCombustivel combustivel;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdemServico> ordensServico = new ArrayList<>();
    
    public enum TipoCombustivel {
        GASOLINA, ETANOL, FLEX, DIESEL, ELETRICO, HIBRIDO
    }
    
    // Construtores
    public Veiculo() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Veiculo(String placa, String marca, String modelo, Integer ano, Cliente cliente) {
        this();
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Integer getAno() {
        return ano;
    }
    
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    
    public String getCor() {
        return cor;
    }
    
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public String getChassi() {
        return chassi;
    }
    
    public void setChassi(String chassi) {
        this.chassi = chassi;
    }
    
    public String getRenavam() {
        return renavam;
    }
    
    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }
    
    public Long getQuilometragem() {
        return quilometragem;
    }
    
    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }
    
    public TipoCombustivel getCombustivel() {
        return combustivel;
    }
    
    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }
    
    public void setOrdensServico(List<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }
    
    // Métodos de negócio
    public void adicionarOrdemServico(OrdemServico ordemServico) {
        ordemServico.setVeiculo(this);
        this.ordensServico.add(ordemServico);
    }
    
    public void removerOrdemServico(OrdemServico ordemServico) {
        this.ordensServico.remove(ordemServico);
        ordemServico.setVeiculo(null);
    }
    
    public void atualizarQuilometragem(Long novaQuilometragem) {
        if (novaQuilometragem != null && novaQuilometragem > 0) {
            this.quilometragem = novaQuilometragem;
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cliente=" + (cliente != null ? cliente.getId() : "null") +
                '}';
    }
} 