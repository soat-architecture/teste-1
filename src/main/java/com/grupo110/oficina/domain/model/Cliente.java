package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente  {

    @Id
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos")
    @Column(name = "documento", nullable = false, unique = true)
    private String documento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;
    
    @Email(message = "Email deve ser válido")
    @Column(name = "email")
    private String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Telefone deve ser válido")
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "cidade")
    private String cidade;
    
    @Column(name = "estado", length = 2)
    private String estado;
    
    @Column(name = "cep")
    private String cep;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Veiculo> veiculos = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdemServico> ordensServico = new ArrayList<>();
    
    public enum TipoDocumento {
        CPF, CNPJ
    }
    
    // Construtores
    public Cliente() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Cliente(String nome, String documento, TipoDocumento tipoDocumento) {
        this();
        this.nome = nome;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
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
    
    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
    
    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
    
    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }
    
    public void setOrdensServico(List<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }
    
    // Métodos de negócio
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculo.setCliente(this);
        this.veiculos.add(veiculo);
    }
    
    public void removerVeiculo(Veiculo veiculo) {
        this.veiculos.remove(veiculo);
        veiculo.setCliente(null);
    }
    
    public void adicionarOrdemServico(OrdemServico ordemServico) {
        ordemServico.setCliente(this);
        this.ordensServico.add(ordemServico);
    }
    
    public void removerOrdemServico(OrdemServico ordemServico) {
        this.ordensServico.remove(ordemServico);
        ordemServico.setCliente(null);
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", tipoDocumento=" + tipoDocumento +
                ", ativo=" + ativo +
                '}';
    }
} 