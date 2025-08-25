package com.grupo110.oficina.application.dto;

import com.grupo110.oficina.domain.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteDTO {
    
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "CPF deve ter 11 dígitos ou CNPJ deve ter 14 dígitos")
    private String documento;
    
    @NotBlank(message = "Tipo de documento é obrigatório")
    private String tipoDocumento;
    
    @Email(message = "Email deve ser válido")
    private String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Telefone deve ser válido")
    private String telefone;
    
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    
    // DTOs relacionados
    private List<VeiculoDTO> veiculos;
    private List<OrdemServicoDTO> ordensServico;
    
    // Construtores
    public ClienteDTO() {}
    
    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.documento = cliente.getDocumento();
        this.tipoDocumento = cliente.getTipoDocumento().name();
        this.email = cliente.getEmail();
        this.telefone = cliente.getTelefone();
        this.endereco = cliente.getEndereco();
        this.cidade = cliente.getCidade();
        this.estado = cliente.getEstado();
        this.cep = cliente.getCep();
        this.ativo = cliente.getAtivo();
        this.dataCadastro = cliente.getDataCadastro();
        this.dataAtualizacao = cliente.getDataAtualizacao();
        
        // Converter veículos se disponível
        if (cliente.getVeiculos() != null && !cliente.getVeiculos().isEmpty()) {
            this.veiculos = cliente.getVeiculos().stream()
                    .map(VeiculoDTO::new)
                    .collect(Collectors.toList());
        }
        
        // Converter ordens de serviço se disponível
        if (cliente.getOrdensServico() != null && !cliente.getOrdensServico().isEmpty()) {
            this.ordensServico = cliente.getOrdensServico().stream()
                    .map(OrdemServicoDTO::new)
                    .collect(Collectors.toList());
        }
    }
    
    // Método para converter DTO para entidade
    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.nome);
        cliente.setDocumento(this.documento);
        cliente.setTipoDocumento(Cliente.TipoDocumento.valueOf(this.tipoDocumento));
        cliente.setEmail(this.email);
        cliente.setTelefone(this.telefone);
        cliente.setEndereco(this.endereco);
        cliente.setCidade(this.cidade);
        cliente.setEstado(this.estado);
        cliente.setCep(this.cep);
        cliente.setAtivo(this.ativo != null ? this.ativo : true);
        return cliente;
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
    
    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(String tipoDocumento) {
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
    
    public List<VeiculoDTO> getVeiculos() {
        return veiculos;
    }
    
    public void setVeiculos(List<VeiculoDTO> veiculos) {
        this.veiculos = veiculos;
    }
    
    public List<OrdemServicoDTO> getOrdensServico() {
        return ordensServico;
    }
    
    public void setOrdensServico(List<OrdemServicoDTO> ordensServico) {
        this.ordensServico = ordensServico;
    }
    
    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", email='" + email + '\'' +
                ", ativo=" + ativo +
                '}';
    }
} 