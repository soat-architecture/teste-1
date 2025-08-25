package com.grupo110.oficina.application.service;

import com.grupo110.oficina.domain.model.Servico;
import com.grupo110.oficina.infrastructure.repository.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ServicoService {
    
    @Inject
    ServicoRepository servicoRepository;
    
    /**
     * Cria um novo serviço
     */
    public Servico criarServico(@Valid Servico servico) {
        // Validar se já existe serviço com o mesmo nome
        if (servicoRepository.existsByNome(servico.getNome())) {
            throw new RuntimeException("Já existe serviço com o nome: " + servico.getNome());
        }
        
        // Validar se o valor base é positivo
        if (servico.getValorBase() == null || servico.getValorBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor base deve ser maior que zero");
        }
        
        // Validar se o tempo médio de execução é positivo
        if (servico.getTempoMedioExecucao() == null || servico.getTempoMedioExecucao() <= 0) {
            throw new RuntimeException("Tempo médio de execução deve ser maior que zero");
        }
        
        servicoRepository.persist(servico);
        return servico;
    }
    
    /**
     * Atualiza um serviço existente
     */
    public Servico atualizarServico(Long id, @Valid Servico servicoAtualizado) {
        Servico servico = servicoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
        
        // Validar se o nome foi alterado e se já existe outro serviço com ele
        if (!servico.getNome().equals(servicoAtualizado.getNome())) {
            Optional<Servico> servicoExistente = servicoRepository.findByNomeAndIdNot(
                    servicoAtualizado.getNome(), id);
            if (servicoExistente.isPresent()) {
                throw new RuntimeException("Já existe outro serviço com o nome: " + servicoAtualizado.getNome());
            }
        }
        
        // Validar se o valor base é positivo
        if (servicoAtualizado.getValorBase() == null || servicoAtualizado.getValorBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor base deve ser maior que zero");
        }
        
        // Validar se o tempo médio de execução é positivo
        if (servicoAtualizado.getTempoMedioExecucao() == null || servicoAtualizado.getTempoMedioExecucao() <= 0) {
            throw new RuntimeException("Tempo médio de execução deve ser maior que zero");
        }
        
        // Atualizar campos
        servico.setNome(servicoAtualizado.getNome());
        servico.setDescricao(servicoAtualizado.getDescricao());
        servico.setValorBase(servicoAtualizado.getValorBase());
        servico.setTempoMedioExecucao(servicoAtualizado.getTempoMedioExecucao());
        servico.setCategoria(servicoAtualizado.getCategoria());
        servico.setAtivo(servicoAtualizado.getAtivo());
        
        servicoRepository.persist(servico);
        return servico;
    }
    
    /**
     * Busca serviço por ID
     */
    public Servico buscarPorId(Long id) {
        return servicoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
    }
    
    /**
     * Busca serviço por nome
     */
    public Servico buscarPorNome(String nome) {
        return servicoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com nome: " + nome));
    }
    
    /**
     * Lista todos os serviços
     */
    public List<Servico> listarTodos() {
        return servicoRepository.listAll();
    }
    
    /**
     * Lista serviços ativos
     */
    public List<Servico> listarAtivos() {
        return servicoRepository.findAtivos();
    }
    
    /**
     * Lista serviços por categoria
     */
    public List<Servico> listarPorCategoria(Servico.CategoriaServico categoria) {
        return servicoRepository.findByCategoriaAndAtivoTrue(categoria);
    }

    
    /**
     * Lista serviços por valor base maior que
     */
    public List<Servico> listarPorValorBaseMaiorQue(BigDecimal valor) {
        return servicoRepository.findByValorBaseGreaterThan(valor);
    }
    
    /**
     * Lista serviços por valor base entre
     */
    public List<Servico> listarPorValorBaseEntre(BigDecimal valorMin, BigDecimal valorMax) {
        return servicoRepository.findByValorBaseBetween(valorMin, valorMax);
    }
    
    /**
     * Lista serviços por tempo médio de execução menor que
     */
    public List<Servico> listarPorTempoExecucaoMenorQue(Integer tempoMinutos) {
        return servicoRepository.findByTempoMedioExecucaoLessThan(tempoMinutos);
    }
    
    /**
     * Lista serviços por tempo médio de execução entre
     */
    public List<Servico> listarPorTempoExecucaoEntre(Integer tempoMin, Integer tempoMax) {
        return servicoRepository.findByTempoMedioExecucaoBetween(tempoMin, tempoMax);
    }
    
    /**
     * Lista serviços ordenados por nome
     */
    public List<Servico> listarOrdenadosPorNome() {
        return servicoRepository.findOrderByNome();
    }
    
    /**
     * Lista serviços ordenados por valor base (menor para maior)
     */
    public List<Servico> listarOrdenadosPorValorBaseAsc() {
        return servicoRepository.findOrderByValorBaseAsc();
    }
    
    /**
     * Lista serviços ordenados por valor base (maior para menor)
     */
    public List<Servico> listarOrdenadosPorValorBaseDesc() {
        return servicoRepository.findOrderByValorBaseDesc();
    }
    
    /**
     * Lista serviços ordenados por categoria e nome
     */
    public List<Servico> listarOrdenadosPorCategoriaENome() {
        return servicoRepository.findOrderByCategoriaAscNomeAsc();
    }
    
    /**
     * Desativa um serviço
     */
    public Servico desativarServico(Long id) {
        Servico servico = buscarPorId(id);
        servico.setAtivo(false);
        servicoRepository.persist(servico);
        return servico;
    }
    
    /**
     * Ativa um serviço
     */
    public Servico ativarServico(Long id) {
        Servico servico = buscarPorId(id);
        servico.setAtivo(true);
        servicoRepository.persist(servico);
        return servico;
    }
    
    /**
     * Remove um serviço (soft delete)
     */
    public void removerServico(Long id) {
        Servico servico = buscarPorId(id);
        servico.setAtivo(false);
        servicoRepository.persist(servico);
    }
    
    /**
     * Atualiza valor base do serviço
     */
    public Servico atualizarValorBase(Long id, BigDecimal novoValor) {
        if (novoValor == null || novoValor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor base deve ser maior que zero");
        }
        
        Servico servico = buscarPorId(id);
        servico.setValorBase(novoValor);
        servicoRepository.persist(servico);
        return servico;
    }
    
    /**
     * Atualiza tempo médio de execução do serviço
     */
    public Servico atualizarTempoExecucao(Long id, Integer novoTempo) {
        if (novoTempo == null || novoTempo <= 0) {
            throw new RuntimeException("Tempo médio de execução deve ser maior que zero");
        }
        
        Servico servico = buscarPorId(id);
        servico.setTempoMedioExecucao(novoTempo);
        servicoRepository.persist(servico);
        return servico;
    }
    
    /**
     * Verifica se serviço existe
     */
    public boolean servicoExiste(Long id) {
        return servicoRepository.findByIdOptional(id).isPresent();
    }
    
    /**
     * Verifica se serviço está ativo
     */
    public boolean servicoAtivo(Long id) {
        Optional<Servico> servico = servicoRepository.findByIdOptional(id);
        return servico.isPresent() && servico.get().getAtivo();
    }
    
    /**
     * Verifica se nome existe
     */
    public boolean nomeExiste(String nome) {
        return servicoRepository.existsByNome(nome);
    }
    
    /**
     * Conta serviços por categoria
     */
    public long contarPorCategoria(Servico.CategoriaServico categoria) {
        return servicoRepository.countByCategoria(categoria);
    }
    
    /**
     * Conta serviços ativos
     */
    public long contarAtivos() {
        return servicoRepository.countByAtivoTrue();
    }
} 