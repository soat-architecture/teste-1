package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfis")
public class Perfil  {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPerfil tipo;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "perfil_permissoes",
        joinColumns = @JoinColumn(name = "perfil_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<Permissao> permissoes = new HashSet<>();
    
    @OneToMany(mappedBy = "perfil")
    private Set<Usuario> usuarios = new HashSet<>();
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public enum TipoPerfil {
        ADMIN("Administrador"),
        GERENTE("Gerente"),
        MECANICO("Mecânico"),
        ATENDENTE("Atendente"),
        CLIENTE("Cliente");
        
        private final String descricao;
        
        TipoPerfil(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Perfil() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Perfil(String nome, String descricao, TipoPerfil tipo) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }
    
    // Getters e Setters
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
    
    public TipoPerfil getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoPerfil tipo) {
        this.tipo = tipo;
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
    
    public Set<Permissao> getPermissoes() {
        return permissoes;
    }
    
    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }
    
    public Set<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    // Métodos de negócio
    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
    }
    
    public void removerPermissao(Permissao permissao) {
        this.permissoes.remove(permissao);
    }
    
    public boolean temPermissao(String nomePermissao) {
        return this.permissoes.stream()
                .anyMatch(permissao -> permissao.getNome().equals(nomePermissao));
    }
    
    public boolean temPermissao(Permissao.TipoPermissao tipoPermissao) {
        return this.permissoes.stream()
                .anyMatch(permissao -> permissao.getTipo() == tipoPermissao);
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", ativo=" + ativo +
                '}';
    }
} 