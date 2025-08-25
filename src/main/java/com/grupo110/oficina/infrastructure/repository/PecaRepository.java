package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Peca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PecaRepository implements PanacheRepository<Peca> {
    
    /**
     * Busca peça por código
     */
    public Optional<Peca> findByCodigo(String codigo) {
        return find("codigo", codigo).firstResultOptional();
    }
    
    /**
     * Busca peça por nome
     */
    public Optional<Peca> findByNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }
    
    /**
     * Busca peças por categoria
     */
    public List<Peca> findByCategoria(Peca.CategoriaPeca categoria) {
        return find("categoria", categoria).list();
    }
    
    /**
     * Busca peças por fabricante
     */
    public List<Peca> findByFabricante(String fabricante) {
        return find("fabricante", fabricante).list();
    }
    
    /**
     * Busca peças por marca de veículo
     */
    public List<Peca> findByMarcaVeiculo(String marcaVeiculo) {
        return find("marcaVeiculo", marcaVeiculo).list();
    }
    
    /**
     * Busca peças por modelo de veículo
     */
    public List<Peca> findByModeloVeiculo(String modeloVeiculo) {
        return find("modeloVeiculo", modeloVeiculo).list();
    }
    
    /**
     * Busca peças por ano de veículo
     */
    public List<Peca> findByAnoInicioLessThanEqualAndAnoFimGreaterThanEqual(Integer ano) {
        return find("anoInicio <= ?1 and anoFim >= ?1", ano).list();
    }
    
    /**
     * Busca peças por nome (busca parcial)
     */
    public List<Peca> findByNomeContainingIgnoreCase(String nome) {
        return find("LOWER(nome) LIKE LOWER(?1)", "%" + nome + "%").list();
    }
    
    /**
     * Busca peças ativas
     */
    public List<Peca> findAtivas() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca peças por categoria ativas
     */
    public List<Peca> findByCategoriaAndAtivoTrue(Peca.CategoriaPeca categoria) {
        return find("categoria = ?1 and ativo = ?2", categoria, true).list();
    }
    
    /**
     * Busca peças com estoque baixo
     */
    public List<Peca> findPecasComEstoqueBaixo() {
        return find("quantidadeEstoque <= estoqueMinimo and ativo = true").list();
    }
    
    /**
     * Busca peças com estoque zero
     */
    public List<Peca> findPecasSemEstoque() {
        return find("quantidadeEstoque = 0 and ativo = true").list();
    }
    
    /**
     * Busca peças por preço de venda maior que
     */
    public List<Peca> findByPrecoVendaGreaterThan(BigDecimal preco) {
        return find("precoVenda > ?1", preco).list();
    }
    
    /**
     * Busca peças por preço de venda entre
     */
    public List<Peca> findByPrecoVendaBetween(BigDecimal precoMin, BigDecimal precoMax) {
        return find("precoVenda between ?1 and ?2", precoMin, precoMax).list();
    }
    
    /**
     * Busca peças por quantidade em estoque menor que
     */
    public List<Peca> findByQuantidadeEstoqueLessThan(Integer quantidade) {
        return find("quantidadeEstoque < ?1", quantidade).list();
    }
    
    /**
     * Verifica se existe peça com o código informado
     */
    public boolean existsByCodigo(String codigo) {
        return count("codigo", codigo) > 0;
    }
    
    /**
     * Verifica se existe peça com o nome informado
     */
    public boolean existsByNome(String nome) {
        return count("nome", nome) > 0;
    }
    
    /**
     * Busca peça por código ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Peca> findByCodigoAndIdNot(String codigo, Long id) {
        return find("codigo = ?1 and id != ?2", codigo, id).firstResultOptional();
    }
    
    /**
     * Busca peça por nome ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Peca> findByNomeAndIdNot(String nome, Long id) {
        return find("nome = ?1 and id != ?2", nome, id).firstResultOptional();
    }
    
    /**
     * Busca peças ordenadas por nome
     */
    public List<Peca> findOrderByNome() {
        return find("order by nome").list();
    }
    
    /**
     * Busca peças ordenadas por preço de venda (menor para maior)
     */
    public List<Peca> findOrderByPrecoVendaAsc() {
        return find("order by precoVenda asc").list();
    }
    
    /**
     * Busca peças ordenadas por preço de venda (maior para menor)
     */
    public List<Peca> findOrderByPrecoVendaDesc() {
        return find("order by precoVenda desc").list();
    }
    
    /**
     * Busca peças ordenadas por categoria e nome
     */
    public List<Peca> findOrderByCategoriaAscNomeAsc() {
        return find("order by categoria asc, nome asc").list();
    }
    
    /**
     * Busca peças ordenadas por quantidade em estoque (menor para maior)
     */
    public List<Peca> findOrderByQuantidadeEstoqueAsc() {
        return find("order by quantidadeEstoque asc").list();
    }
    
    /**
     * Conta peças por categoria
     */
    public long countByCategoria(Peca.CategoriaPeca categoria) {
        return count("categoria", categoria);
    }
    
    /**
     * Conta peças ativas
     */
    public long countByAtivoTrue() {
        return count("ativo", true);
    }
    
    /**
     * Conta peças com estoque baixo
     */
    public long countPecasComEstoqueBaixo() {
        return count("quantidadeEstoque <= estoqueMinimo and ativo = true");
    }
    
    /**
     * Conta peças sem estoque
     */
    public long countPecasSemEstoque() {
        return count("quantidadeEstoque = 0 and ativo = true");
    }
} 