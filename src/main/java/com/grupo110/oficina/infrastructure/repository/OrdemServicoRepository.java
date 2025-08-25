package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.OrdemServico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrdemServicoRepository implements PanacheRepository<OrdemServico> {
    
    /**
     * Busca ordem de serviço por número
     */
    public Optional<OrdemServico> findByNumeroOS(String numeroOS) {
        return find("numeroOS", numeroOS).firstResultOptional();
    }
    
    /**
     * Busca ordens de serviço por cliente
     */
    public List<OrdemServico> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).list();
    }
    
    /**
     * Busca ordens de serviço por veículo
     */
    public List<OrdemServico> findByVeiculoId(Long veiculoId) {
        return find("veiculo.id", veiculoId).list();
    }
    
    /**
     * Busca ordens de serviço por status
     */
    public List<OrdemServico> findByStatus(OrdemServico.StatusOrdemServico status) {
        return find("status", status).list();
    }
    
    /**
     * Busca ordens de serviço por cliente e status
     */
    public List<OrdemServico> findByClienteIdAndStatus(Long clienteId, OrdemServico.StatusOrdemServico status) {
        return find("cliente.id = ?1 and status = ?2", clienteId, status).list();
    }
    
    /**
     * Busca ordens de serviço por veículo e status
     */
    public List<OrdemServico> findByVeiculoIdAndStatus(Long veiculoId, OrdemServico.StatusOrdemServico status) {
        return find("veiculo.id = ?1 and status = ?2", veiculoId, status).list();
    }
    
    /**
     * Busca ordens de serviço por período de data de recebimento
     */
    public List<OrdemServico> findByDataRecebimentoBetween(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("dataRecebimento between ?1 and ?2", dataInicio, dataFim).list();
    }
    
    /**
     * Busca ordens de serviço por período de data de finalização
     */
    public List<OrdemServico> findByDataFinalizacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("dataFinalizacao between ?1 and ?2", dataInicio, dataFim).list();
    }
    
    /**
     * Busca ordens de serviço por valor total maior que
     */
    public List<OrdemServico> findByValorTotalGreaterThan(java.math.BigDecimal valor) {
        return find("valorTotal > ?1", valor).list();
    }
    
    /**
     * Busca ordens de serviço por valor total entre
     */
    public List<OrdemServico> findByValorTotalBetween(java.math.BigDecimal valorMin, java.math.BigDecimal valorMax) {
        return find("valorTotal between ?1 and ?2", valorMin, valorMax).list();
    }
    
    /**
     * Busca ordens de serviço atrasadas (data atual > prazo entrega)
     */
    public List<OrdemServico> findOrdensAtrasadas() {
        LocalDateTime agora = LocalDateTime.now();
        return find("prazoEntrega < ?1 and status != ?2", agora, OrdemServico.StatusOrdemServico.FINALIZADA).list();
    }
    
    /**
     * Busca ordens de serviço em execução há mais de X dias
     */
    public List<OrdemServico> findOrdensEmExecucaoAntigas(int diasLimite) {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(diasLimite);
        return find("status = ?1 and dataInicioExecucao < ?2", OrdemServico.StatusOrdemServico.EM_EXECUCAO, dataLimite).list();
    }
    
    /**
     * Verifica se existe ordem de serviço com o número informado
     */
    public boolean existsByNumeroOS(String numeroOS) {
        return count("numeroOS", numeroOS) > 0;
    }
    
    /**
     * Busca ordem de serviço por número ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<OrdemServico> findByNumeroOSAndIdNot(String numeroOS, Long id) {
        return find("numeroOS = ?1 and id != ?2", numeroOS, id).firstResultOptional();
    }
    
    /**
     * Conta ordens de serviço por status
     */
    public long countByStatus(OrdemServico.StatusOrdemServico status) {
        return count("status", status);
    }
    
    /**
     * Conta ordens de serviço por cliente
     */
    public long countByClienteId(Long clienteId) {
        return count("cliente.id", clienteId);
    }
    
    /**
     * Busca ordens de serviço ordenadas por data de recebimento (mais recentes primeiro)
     */
    public List<OrdemServico> findOrderByDataRecebimentoDesc() {
        return find("order by dataRecebimento desc").list();
    }
    
    /**
     * Busca ordens de serviço por cliente ordenadas por data de recebimento (mais recentes primeiro)
     */
    public List<OrdemServico> findByClienteIdOrderByDataRecebimentoDesc(Long clienteId) {
        return find("cliente.id = ?1 order by dataRecebimento desc", clienteId).list();
    }
} 