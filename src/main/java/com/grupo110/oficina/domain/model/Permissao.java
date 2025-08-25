package com.grupo110.oficina.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissoes")
public class Permissao {

    @Id
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;
    
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoPermissao tipo;
    
    @Column(name = "recurso")
    private String recurso;
    
    @Column(name = "acao")
    private String acao;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @ManyToMany(mappedBy = "permissoes")
    private Set<Perfil> perfis = new HashSet<>();


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public enum TipoPermissao {
        CRUD_CLIENTE("CRUD Cliente"),
        CRUD_VEICULO("CRUD Veículo"),
        CRUD_SERVICO("CRUD Serviço"),
        CRUD_PECA("CRUD Peça"),
        CRUD_ORDEM_SERVICO("CRUD Ordem de Serviço"),
        CRUD_USUARIO("CRUD Usuário"),
        CRUD_PERFIL("CRUD Perfil"),
        CRUD_PERMISSAO("CRUD Permissão"),
        VISUALIZAR_RELATORIOS("Visualizar Relatórios"),
        GERENCIAR_ESTOQUE("Gerenciar Estoque"),
        APROVAR_ORDENS("Aprovar Ordens"),
        EXECUTAR_SERVICOS("Executar Serviços"),
        ATENDER_CLIENTES("Atender Clientes");
        
        private final String descricao;
        
        TipoPermissao(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Permissao() {
        this.dataCadastro = LocalDateTime.now();
    }
    
    public Permissao(String nome, String descricao, TipoPermissao tipo, String recurso, String acao) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.recurso = recurso;
        this.acao = acao;
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
    
    public TipoPermissao getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoPermissao tipo) {
        this.tipo = tipo;
    }
    
    public String getRecurso() {
        return recurso;
    }
    
    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }
    
    public String getAcao() {
        return acao;
    }
    
    public void setAcao(String acao) {
        this.acao = acao;
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
    
    public Set<Perfil> getPerfis() {
        return perfis;
    }
    
    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }
    
    // Métodos de negócio
    public void adicionarPerfil(Perfil perfil) {
        this.perfis.add(perfil);
        perfil.getPermissoes().add(this);
    }
    
    public void removerPerfil(Perfil perfil) {
        this.perfis.remove(perfil);
        perfil.getPermissoes().remove(this);
    }
    
    public String getPermissaoCompleta() {
        return recurso + ":" + acao;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Permissao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", recurso='" + recurso + '\'' +
                ", acao='" + acao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
} 