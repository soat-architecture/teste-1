package com.grupo110.oficina.application.service;

import com.grupo110.oficina.domain.model.*;
import com.grupo110.oficina.infrastructure.repository.OrdemServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class OrdemServicoService {
    
    @Inject
    OrdemServicoRepository ordemServicoRepository;
    
    @Inject
    ClienteService clienteService;
    
    @Inject
    VeiculoService veiculoService;
    
    /**
     * Cria uma nova ordem de serviço
     */
    public OrdemServico criarOrdemServico(@Valid OrdemServico ordemServico) {
        // Validar se o cliente existe e está ativo
        if (!clienteService.clienteAtivo(ordemServico.getCliente().getId())) {
            throw new RuntimeException("Cliente não encontrado ou inativo");
        }
        
        // Validar se o veículo existe e está ativo
        if (!veiculoService.veiculoAtivo(ordemServico.getVeiculo().getId())) {
            throw new RuntimeException("Veículo não encontrado ou inativo");
        }
        
        // Validar se o veículo pertence ao cliente
        if (!ordemServico.getVeiculo().getCliente().getId().equals(ordemServico.getCliente().getId())) {
            throw new RuntimeException("Veículo não pertence ao cliente informado");
        }
        
        // Gerar número da OS automaticamente
        ordemServico.setNumeroOS(gerarNumeroOS());
        
        // Definir status inicial
        ordemServico.setStatus(OrdemServico.StatusOrdemServico.RECEBIDA);
        
        // Definir data de recebimento
        ordemServico.setDataRecebimento(LocalDateTime.now());
        
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Atualiza uma ordem de serviço existente
     */
    public OrdemServico atualizarOrdemServico(Long id, @Valid OrdemServico ordemServicoAtualizada) {
        OrdemServico ordemServico = ordemServicoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com ID: " + id));
        
        // Validar se o cliente foi alterado e se está ativo
        if (!ordemServico.getCliente().getId().equals(ordemServicoAtualizada.getCliente().getId())) {
            if (!clienteService.clienteAtivo(ordemServicoAtualizada.getCliente().getId())) {
                throw new RuntimeException("Cliente não encontrado ou inativo");
            }
        }
        
        // Validar se o veículo foi alterado e se está ativo
        if (!ordemServico.getVeiculo().getId().equals(ordemServicoAtualizada.getVeiculo().getId())) {
            if (!veiculoService.veiculoAtivo(ordemServicoAtualizada.getVeiculo().getId())) {
                throw new RuntimeException("Veículo não encontrado ou inativo");
            }
        }
        
        // Atualizar campos permitidos
        ordemServico.setDescricaoProblema(ordemServicoAtualizada.getDescricaoProblema());
        ordemServico.setDiagnostico(ordemServicoAtualizada.getDiagnostico());
        ordemServico.setObservacoes(ordemServicoAtualizada.getObservacoes());
        ordemServico.setPrazoEntrega(ordemServicoAtualizada.getPrazoEntrega());
        ordemServico.setCliente(ordemServicoAtualizada.getCliente());
        ordemServico.setVeiculo(ordemServicoAtualizada.getVeiculo());
        
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Busca ordem de serviço por ID
     */
    public OrdemServico buscarPorId(Long id) {
        return ordemServicoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com ID: " + id));
    }
    
    /**
     * Busca ordem de serviço por número
     */
    public OrdemServico buscarPorNumero(String numeroOS) {
        return ordemServicoRepository.findByNumeroOS(numeroOS)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com número: " + numeroOS));
    }
    
    /**
     * Lista todas as ordens de serviço
     */
    public List<OrdemServico> listarTodas() {
        return ordemServicoRepository.listAll();
    }
    
    /**
     * Lista ordens de serviço por cliente
     */
    public List<OrdemServico> listarPorCliente(Long clienteId) {
        return ordemServicoRepository.findByClienteId(clienteId);
    }
    
    /**
     * Lista ordens de serviço por veículo
     */
    public List<OrdemServico> listarPorVeiculo(Long veiculoId) {
        return ordemServicoRepository.findByVeiculoId(veiculoId);
    }
    
    /**
     * Lista ordens de serviço por status
     */
    public List<OrdemServico> listarPorStatus(OrdemServico.StatusOrdemServico status) {
        return ordemServicoRepository.findByStatus(status);
    }
    
    /**
     * Lista ordens de serviço por cliente e status
     */
    public List<OrdemServico> listarPorClienteEStatus(Long clienteId, OrdemServico.StatusOrdemServico status) {
        return ordemServicoRepository.findByClienteIdAndStatus(clienteId, status);
    }
    
    /**
     * Lista ordens de serviço por período de data de recebimento
     */
    public List<OrdemServico> listarPorPeriodoRecebimento(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return ordemServicoRepository.findByDataRecebimentoBetween(dataInicio, dataFim);
    }
    
    /**
     * Lista ordens de serviço por período de data de finalização
     */
    public List<OrdemServico> listarPorPeriodoFinalizacao(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return ordemServicoRepository.findByDataFinalizacaoBetween(dataInicio, dataFim);
    }
    
    /**
     * Lista ordens de serviço por valor total maior que
     */
    public List<OrdemServico> listarPorValorTotalMaiorQue(BigDecimal valor) {
        return ordemServicoRepository.findByValorTotalGreaterThan(valor);
    }
    
    /**
     * Lista ordens de serviço por valor total entre
     */
    public List<OrdemServico> listarPorValorTotalEntre(BigDecimal valorMin, BigDecimal valorMax) {
        return ordemServicoRepository.findByValorTotalBetween(valorMin, valorMax);
    }
    
    /**
     * Lista ordens de serviço atrasadas
     */
    public List<OrdemServico> listarOrdensAtrasadas() {
        return ordemServicoRepository.findOrdensAtrasadas();
    }
    
    /**
     * Lista ordens de serviço em execução há mais de X dias
     */
    public List<OrdemServico> listarOrdensEmExecucaoAntigas(int diasLimite) {
        return ordemServicoRepository.findOrdensEmExecucaoAntigas(diasLimite);
    }
    
    /**
     * Lista ordens de serviço ordenadas por data de recebimento (mais recentes primeiro)
     */
    public List<OrdemServico> listarOrdenadasPorDataRecebimento() {
        return ordemServicoRepository.findOrderByDataRecebimentoDesc();
    }
    
    /**
     * Lista ordens de serviço por cliente ordenadas por data de recebimento (mais recentes primeiro)
     */
    public List<OrdemServico> listarPorClienteOrdenadasPorDataRecebimento(Long clienteId) {
        return ordemServicoRepository.findByClienteIdOrderByDataRecebimentoDesc(clienteId);
    }
    
    /**
     * Altera o status da ordem de serviço
     */
    public OrdemServico alterarStatus(Long id, OrdemServico.StatusOrdemServico novoStatus) {
        OrdemServico ordemServico = buscarPorId(id);
        
        // Validar transições de status permitidas
        validarTransicaoStatus(ordemServico.getStatus(), novoStatus);
        
        // Aplicar o novo status
        ordemServico.setStatus(novoStatus);
        
        // Definir datas específicas baseadas no status
        switch (novoStatus) {
            case EM_EXECUCAO:
                ordemServico.setDataInicioExecucao(LocalDateTime.now());
                break;
            case FINALIZADA:
                ordemServico.setDataFinalizacao(LocalDateTime.now());
                break;
            case ENTREGUE:
                ordemServico.setDataEntrega(LocalDateTime.now());
                break;
        }
        
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Inicia diagnóstico da ordem de serviço
     */
    public OrdemServico iniciarDiagnostico(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServico.iniciarDiagnostico();
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Aguarda aprovação da ordem de serviço
     */
    public OrdemServico aguardarAprovacao(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServico.aguardarAprovacao();
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Inicia execução da ordem de serviço
     */
    public OrdemServico iniciarExecucao(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServico.iniciarExecucao();
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Finaliza a ordem de serviço
     */
    public OrdemServico finalizar(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServico.finalizar();
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Entrega a ordem de serviço
     */
    public OrdemServico entregar(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServico.entregar();
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Calcula valores da ordem de serviço
     */
    public OrdemServico calcularValores(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServico.calcularValores();
        ordemServicoRepository.persist(ordemServico);
        return ordemServico;
    }
    
    /**
     * Remove uma ordem de serviço
     */
    public void removerOrdemServico(Long id) {
        OrdemServico ordemServico = buscarPorId(id);
        ordemServicoRepository.delete(ordemServico);
    }
    
    /**
     * Verifica se ordem de serviço existe
     */
    public boolean ordemServicoExiste(Long id) {
        return ordemServicoRepository.findByIdOptional(id).isPresent();
    }
    
    /**
     * Verifica se número da OS existe
     */
    public boolean numeroOSExiste(String numeroOS) {
        return ordemServicoRepository.existsByNumeroOS(numeroOS);
    }
    
    /**
     * Conta ordens de serviço por status
     */
    public long contarPorStatus(OrdemServico.StatusOrdemServico status) {
        return ordemServicoRepository.countByStatus(status);
    }
    
    /**
     * Conta ordens de serviço por cliente
     */
    public long contarPorCliente(Long clienteId) {
        return ordemServicoRepository.countByClienteId(clienteId);
    }
    
    /**
     * Gera número único para a ordem de serviço
     */
    private String gerarNumeroOS() {
        String numeroOS;
        do {
            numeroOS = "OS" + System.currentTimeMillis();
        } while (ordemServicoRepository.existsByNumeroOS(numeroOS));
        return numeroOS;
    }
    
    /**
     * Valida transições de status permitidas
     */
    private void validarTransicaoStatus(OrdemServico.StatusOrdemServico statusAtual, OrdemServico.StatusOrdemServico novoStatus) {
        boolean transicaoValida = switch (statusAtual) {
            case RECEBIDA -> novoStatus == OrdemServico.StatusOrdemServico.EM_DIAGNOSTICO;
            case EM_DIAGNOSTICO -> novoStatus == OrdemServico.StatusOrdemServico.AGUARDANDO_APROVACAO;
            case AGUARDANDO_APROVACAO -> novoStatus == OrdemServico.StatusOrdemServico.EM_EXECUCAO;
            case EM_EXECUCAO -> novoStatus == OrdemServico.StatusOrdemServico.FINALIZADA;
            case FINALIZADA -> novoStatus == OrdemServico.StatusOrdemServico.ENTREGUE;
            case ENTREGUE -> false; // Não pode alterar status após entrega
        };
        
        if (!transicaoValida) {
            throw new RuntimeException("Transição de status inválida: " + statusAtual + " -> " + novoStatus);
        }
    }
} 