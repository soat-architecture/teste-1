package com.grupo110.oficina.application.dto;

import com.grupo110.oficina.domain.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class VeiculoDTO {
    
    private Long id;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Placa deve estar no formato Mercosul (ABC1D23)")
    private String placa;
    
    @NotBlank(message = "Marca é obrigatória")
    @Size(min = 2, max = 50, message = "Marca deve ter entre 2 e 50 caracteres")
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Size(min = 2, max = 100, message = "Modelo deve ter entre 2 e 100 caracteres")
    private String modelo;
    
    @NotNull(message = "Ano é obrigatório")
    private Integer ano;
    
    private String cor;
    private String chassi;
    private String renavam;
    private Long quilometragem;
    private String combustivel;
    private String observacoes;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    
    // Relacionamentos
    private Long clienteId;
    private String clienteNome;
    
    // Construtores
    public VeiculoDTO() {}
    
    public VeiculoDTO(Veiculo veiculo) {
        this.id = veiculo.getId();
        this.placa = veiculo.getPlaca();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.ano = veiculo.getAno();
        this.cor = veiculo.getCor();
        this.chassi = veiculo.getChassi();
        this.renavam = veiculo.getRenavam();
        this.quilometragem = veiculo.getQuilometragem();
        this.combustivel = veiculo.getCombustivel() != null ? veiculo.getCombustivel().name() : null;
        this.observacoes = veiculo.getObservacoes();
        this.ativo = veiculo.getAtivo();
        this.dataCadastro = veiculo.getDataCadastro();
        this.dataAtualizacao = veiculo.getDataAtualizacao();
        
        if (veiculo.getCliente() != null) {
            this.clienteId = veiculo.getCliente().getId();
            this.clienteNome = veiculo.getCliente().getNome();
        }
    }
    
    // Método para converter DTO para entidade
    public Veiculo toEntity() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(this.id);
        veiculo.setPlaca(this.placa);
        veiculo.setMarca(this.marca);
        veiculo.setModelo(this.modelo);
        veiculo.setAno(this.ano);
        veiculo.setCor(this.cor);
        veiculo.setChassi(this.chassi);
        veiculo.setRenavam(this.renavam);
        veiculo.setQuilometragem(this.quilometragem);
        if (this.combustivel != null) {
            veiculo.setCombustivel(Veiculo.TipoCombustivel.valueOf(this.combustivel));
        }
        veiculo.setObservacoes(this.observacoes);
        veiculo.setAtivo(this.ativo != null ? this.ativo : true);
        return veiculo;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getCombustivel() {
        return combustivel;
    }
    
    public void setCombustivel(String combustivel) {
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
    
    @Override
    public String toString() {
        return "VeiculoDTO{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", clienteId=" + clienteId +
                '}';
    }
} 