package com.grupo110.oficina.application.service;

import com.grupo110.oficina.domain.model.Peca;
import com.grupo110.oficina.infrastructure.repository.PecaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PecaService {
    
    @Inject
    PecaRepository pecaRepository;
    
    /**
     * Cria uma nova peça
     */
    public Peca criarPeca(@Valid Peca peca) {
        // Validar se já existe peça com o mesmo código
        if (pecaRepository.existsByCodigo(peca.getCodigo())) {
            throw new RuntimeException("Já existe peça com o código: " + peca.getCodigo());
        }
        
        // Validar se já existe peça com o mesmo nome
        if (pecaRepository.existsByNome(peca.getNome())) {
            throw new RuntimeException("Já existe peça com o nome: " + peca.getNome());
        }
        
        // Validar se o preço de custo é positivo
        if (peca.getPrecoCusto() == null || peca.getPrecoCusto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço de custo deve ser maior que zero");
        }
        
        // Validar se o preço de venda é maior que o custo
        if (peca.getPrecoVenda() == null || peca.getPrecoVenda().compareTo(peca.getPrecoCusto()) <= 0) {
            throw new RuntimeException("Preço de venda deve ser maior que o preço de custo");
        }
        
        // Validar se a quantidade em estoque é não negativa
        if (peca.getQuantidadeEstoque() == null || peca.getQuantidadeEstoque() < 0) {
            throw new RuntimeException("Quantidade em estoque deve ser não negativa");
        }
        
        // Validar se a quantidade mínima é não negativa
        if (peca.getQuantidadeMinima() == null || peca.getQuantidadeMinima() < 0) {
            throw new RuntimeException("Quantidade mínima deve ser não negativa");
        }
        
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Atualiza uma peça existente
     */
    public Peca atualizarPeca(Long id, @Valid Peca pecaAtualizada) {
        Peca peca = pecaRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada com ID: " + id));
        
        // Validar se o código foi alterado e se já existe outra peça com ele
        if (!peca.getCodigo().equals(pecaAtualizada.getCodigo())) {
            Optional<Peca> pecaExistente = pecaRepository.findByCodigoAndIdNot(
                    pecaAtualizada.getCodigo(), id);
            if (pecaExistente.isPresent()) {
                throw new RuntimeException("Já existe outra peça com o código: " + pecaAtualizada.getCodigo());
            }
        }
        
        // Validar se o nome foi alterado e se já existe outra peça com ele
        if (!peca.getNome().equals(pecaAtualizada.getNome())) {
            Optional<Peca> pecaExistente = pecaRepository.findByNomeAndIdNot(
                    pecaAtualizada.getNome(), id);
            if (pecaExistente.isPresent()) {
                throw new RuntimeException("Já existe outra peça com o nome: " + pecaAtualizada.getNome());
            }
        }
        
        // Validar se o preço de custo é positivo
        if (pecaAtualizada.getPrecoCusto() == null || pecaAtualizada.getPrecoCusto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço de custo deve ser maior que zero");
        }
        
        // Validar se o preço de venda é maior que o custo
        if (pecaAtualizada.getPrecoVenda() == null || pecaAtualizada.getPrecoVenda().compareTo(pecaAtualizada.getPrecoCusto()) <= 0) {
            throw new RuntimeException("Preço de venda deve ser maior que o preço de custo");
        }
        
        // Validar se a quantidade em estoque é não negativa
        if (pecaAtualizada.getQuantidadeEstoque() == null || pecaAtualizada.getQuantidadeEstoque() < 0) {
            throw new RuntimeException("Quantidade em estoque deve ser não negativa");
        }
        
        // Validar se a quantidade mínima é não negativa
        if (pecaAtualizada.getQuantidadeMinima() == null || pecaAtualizada.getQuantidadeMinima() < 0) {
            throw new RuntimeException("Quantidade mínima deve ser não negativa");
        }
        
        // Atualizar campos
        peca.setCodigo(pecaAtualizada.getCodigo());
        peca.setNome(pecaAtualizada.getNome());
        peca.setDescricao(pecaAtualizada.getDescricao());
        peca.setFabricante(pecaAtualizada.getFabricante());
        peca.setMarcaVeiculo(pecaAtualizada.getMarcaVeiculo());
        peca.setModeloVeiculo(pecaAtualizada.getModeloVeiculo());
        peca.setAnoInicio(pecaAtualizada.getAnoInicio());
        peca.setAnoFim(pecaAtualizada.getAnoFim());
        peca.setPrecoCusto(pecaAtualizada.getPrecoCusto());
        peca.setPrecoVenda(pecaAtualizada.getPrecoVenda());
        peca.setQuantidadeEstoque(pecaAtualizada.getQuantidadeEstoque());
        peca.setQuantidadeMinima(pecaAtualizada.getQuantidadeMinima());
        peca.setUnidadeMedida(pecaAtualizada.getUnidadeMedida());
        peca.setLocalizacaoEstoque(pecaAtualizada.getLocalizacaoEstoque());
        peca.setCategoria(pecaAtualizada.getCategoria());
        peca.setAtivo(pecaAtualizada.getAtivo());
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Busca peça por ID
     */
    public Peca buscarPorId(Long id) {
        return pecaRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada com ID: " + id));
    }
    
    /**
     * Busca peça por código
     */
    public Peca buscarPorCodigo(String codigo) {
        return pecaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada com código: " + codigo));
    }
    
    /**
     * Busca peça por nome
     */
    public Peca buscarPorNome(String nome) {
        return pecaRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada com nome: " + nome));
    }
    
    /**
     * Lista todas as peças
     */
    public List<Peca> listarTodas() {
        return pecaRepository.listAll();
    }
    
    /**
     * Lista peças ativas
     */
    public List<Peca> listarAtivas() {
        return pecaRepository.findAtivas();
    }
    
    /**
     * Lista peças por categoria
     */
    public List<Peca> listarPorCategoria(Peca.CategoriaPeca categoria) {
        return pecaRepository.findByCategoriaAndAtivoTrue(categoria);
    }
    
    /**
     * Lista peças por fabricante
     */
    public List<Peca> listarPorFabricante(String fabricante) {
        return pecaRepository.findByFabricante(fabricante);
    }
    
    /**
     * Lista peças por marca de veículo
     */
    public List<Peca> listarPorMarcaVeiculo(String marcaVeiculo) {
        return pecaRepository.findByMarcaVeiculo(marcaVeiculo);
    }
    
    /**
     * Lista peças por modelo de veículo
     */
    public List<Peca> listarPorModeloVeiculo(String modeloVeiculo) {
        return pecaRepository.findByModeloVeiculo(modeloVeiculo);
    }
    
    /**
     * Lista peças por ano de veículo
     */
    public List<Peca> listarPorAnoVeiculo(Integer ano) {
        return pecaRepository.findByAnoInicioLessThanEqualAndAnoFimGreaterThanEqual(ano);
    }
    
    /**
     * Lista peças com estoque baixo
     */
    public List<Peca> listarComEstoqueBaixo() {
        return pecaRepository.findPecasComEstoqueBaixo();
    }
    
    /**
     * Lista peças sem estoque
     */
    public List<Peca> listarSemEstoque() {
        return pecaRepository.findPecasSemEstoque();
    }
    
    /**
     * Lista peças por preço de venda maior que
     */
    public List<Peca> listarPorPrecoVendaMaiorQue(BigDecimal preco) {
        return pecaRepository.findByPrecoVendaGreaterThan(preco);
    }
    
    /**
     * Lista peças por preço de venda entre
     */
    public List<Peca> listarPorPrecoVendaEntre(BigDecimal precoMin, BigDecimal precoMax) {
        return pecaRepository.findByPrecoVendaBetween(precoMin, precoMax);
    }
    
    /**
     * Lista peças por quantidade em estoque menor que
     */
    public List<Peca> listarPorQuantidadeEstoqueMenorQue(Integer quantidade) {
        return pecaRepository.findByQuantidadeEstoqueLessThan(quantidade);
    }
    
    /**
     * Lista peças ordenadas por nome
     */
    public List<Peca> listarOrdenadasPorNome() {
        return pecaRepository.findOrderByNome();
    }
    
    /**
     * Lista peças ordenadas por preço de venda (menor para maior)
     */
    public List<Peca> listarOrdenadasPorPrecoVendaAsc() {
        return pecaRepository.findOrderByPrecoVendaAsc();
    }
    
    /**
     * Lista peças ordenadas por preço de venda (maior para menor)
     */
    public List<Peca> listarOrdenadasPorPrecoVendaDesc() {
        return pecaRepository.findOrderByPrecoVendaDesc();
    }
    
    /**
     * Lista peças ordenadas por categoria e nome
     */
    public List<Peca> listarOrdenadasPorCategoriaENome() {
        return pecaRepository.findOrderByCategoriaAscNomeAsc();
    }
    
    /**
     * Lista peças ordenadas por quantidade em estoque (menor para maior)
     */
    public List<Peca> listarOrdenadasPorQuantidadeEstoque() {
        return pecaRepository.findOrderByQuantidadeEstoqueAsc();
    }
    
    /**
     * Desativa uma peça
     */
    public Peca desativarPeca(Long id) {
        Peca peca = buscarPorId(id);
        peca.setAtivo(false);
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Ativa uma peça
     */
    public Peca ativarPeca(Long id) {
        Peca peca = buscarPorId(id);
        peca.setAtivo(true);
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Remove uma peça (soft delete)
     */
    public void removerPeca(Long id) {
        Peca peca = buscarPorId(id);
        peca.setAtivo(false);
        pecaRepository.persist(peca);
    }
    
    /**
     * Atualiza estoque da peça
     */
    public Peca atualizarEstoque(Long id, Integer novaQuantidade) {
        if (novaQuantidade == null || novaQuantidade < 0) {
            throw new RuntimeException("Quantidade em estoque deve ser não negativa");
        }
        
        Peca peca = buscarPorId(id);
        peca.setQuantidadeEstoque(novaQuantidade);
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Adiciona ao estoque da peça
     */
    public Peca adicionarEstoque(Long id, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade a adicionar deve ser maior que zero");
        }
        
        Peca peca = buscarPorId(id);
        peca.adicionarEstoque(quantidade);
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Remove do estoque da peça
     */
    public Peca removerEstoque(Long id, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade a remover deve ser maior que zero");
        }
        
        Peca peca = buscarPorId(id);
        peca.removerEstoque(quantidade);
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Atualiza preços da peça
     */
    public Peca atualizarPrecos(Long id, BigDecimal novoPrecoCusto, BigDecimal novoPrecoVenda) {
        if (novoPrecoCusto == null || novoPrecoCusto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço de custo deve ser maior que zero");
        }
        
        if (novoPrecoVenda == null || novoPrecoVenda.compareTo(novoPrecoCusto) <= 0) {
            throw new RuntimeException("Preço de venda deve ser maior que o preço de custo");
        }
        
        Peca peca = buscarPorId(id);
        peca.setPrecoCusto(novoPrecoCusto);
        peca.setPrecoVenda(novoPrecoVenda);
        pecaRepository.persist(peca);
        return peca;
    }
    
    /**
     * Verifica se peça existe
     */
    public boolean pecaExiste(Long id) {
        return pecaRepository.findByIdOptional(id).isPresent();
    }
    
    /**
     * Verifica se peça está ativa
     */
    public boolean pecaAtiva(Long id) {
        Optional<Peca> peca = pecaRepository.findByIdOptional(id);
        return peca.isPresent() && peca.get().getAtivo();
    }
    
    /**
     * Verifica se código existe
     */
    public boolean codigoExiste(String codigo) {
        return pecaRepository.existsByCodigo(codigo);
    }
    
    /**
     * Verifica se nome existe
     */
    public boolean nomeExiste(String nome) {
        return pecaRepository.existsByNome(nome);
    }
    
    /**
     * Conta peças por categoria
     */
    public long contarPorCategoria(Peca.CategoriaPeca categoria) {
        return pecaRepository.countByCategoria(categoria);
    }
    
    /**
     * Conta peças ativas
     */
    public long contarAtivas() {
        return pecaRepository.countByAtivoTrue();
    }
    
    /**
     * Conta peças com estoque baixo
     */
    public long contarComEstoqueBaixo() {
        return pecaRepository.countPecasComEstoqueBaixo();
    }
    
    /**
     * Conta peças sem estoque
     */
    public long contarSemEstoque() {
        return pecaRepository.countPecasSemEstoque();
    }
} 