package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.HistoricoStatus;
import com.grupo110.oficina.domain.model.OrdemServico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class HistoricoStatusRepository implements PanacheRepository<HistoricoStatus> {
    
    /**
     * Busca histórico por ordem de serviço
     */
    public List<HistoricoStatus> findByOrdemServicoId(Long ordemServicoId) {
        return find("ordemServico.id", ordemServicoId).list();
    }
    
    /**
     * Busca histórico por ordem de serviço ordenado por data (mais recentes primeiro)
     */
    public List<HistoricoStatus> findByOrdemServicoIdOrderByDataAlteracaoDesc(Long ordemServicoId) {
        return find("ordemServico.id = ?1 order by dataAlteracao desc", ordemServicoId).list();
    }
    
    /**
     * Busca histórico por status
     */
    public List<HistoricoStatus> findByStatus(OrdemServico.StatusOrdemServico status) {
        return find("status", status).list();
    }
    
    /**
     * Busca histórico por período de data de alteração
     */
    public List<HistoricoStatus> findByDataAlteracaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("dataAlteracao between ?1 and ?2", dataInicio, dataFim).list();
    }
    
    /**
     * Busca histórico por usuário que fez a alteração
     */
    public List<HistoricoStatus> findByUsuarioId(Long usuarioId) {
        return find("usuario.id", usuarioId).list();
    }
    
    /**
     * Busca histórico por usuário e período
     */
    public List<HistoricoStatus> findByUsuarioIdAndDataAlteracaoBetween(Long usuarioId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("usuario.id = ?1 and dataAlteracao between ?2 and ?3", usuarioId, dataInicio, dataFim).list();
    }
    
    /**
     * Busca histórico por ordem de serviço e status
     */
    public List<HistoricoStatus> findByOrdemServicoIdAndStatus(Long ordemServicoId, OrdemServico.StatusOrdemServico status) {
        return find("ordemServico.id = ?1 and status = ?2", ordemServicoId, status).list();
    }
    
    /**
     * Busca histórico por ordem de serviço e período
     */
    public List<HistoricoStatus> findByOrdemServicoIdAndDataAlteracaoBetween(Long ordemServicoId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("ordemServico.id = ?1 and dataAlteracao between ?2 and ?3", ordemServicoId, dataInicio, dataFim).list();
    }
    
    /**
     * Busca histórico por status e período
     */
    public List<HistoricoStatus> findByStatusAndDataAlteracaoBetween(OrdemServico.StatusOrdemServico status, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("status = ?1 and dataAlteracao between ?2 and ?3", status, dataInicio, dataFim).list();
    }
    
    /**
     * Busca histórico por usuário e status
     */
    public List<HistoricoStatus> findByUsuarioIdAndStatus(Long usuarioId, OrdemServico.StatusOrdemServico status) {
        return find("usuario.id = ?1 and status = ?2", usuarioId, status).list();
    }
    
    /**
     * Busca histórico por ordem de serviço, status e período
     */
    public List<HistoricoStatus> findByOrdemServicoIdAndStatusAndDataAlteracaoBetween(Long ordemServicoId, OrdemServico.StatusOrdemServico status, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("ordemServico.id = ?1 and status = ?2 and dataAlteracao between ?3 and ?4", ordemServicoId, status, dataInicio, dataFim).list();
    }
    
    /**
     * Busca histórico por usuário, status e período
     */
    public List<HistoricoStatus> findByUsuarioIdAndStatusAndDataAlteracaoBetween(Long usuarioId, OrdemServico.StatusOrdemServico status, LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("usuario.id = ?1 and status = ?2 and dataAlteracao between ?3 and ?4", usuarioId, status, dataInicio, dataFim).list();
    }
    
    /**
     * Busca histórico ordenado por data de alteração (mais recentes primeiro)
     */
    public List<HistoricoStatus> findOrderByDataAlteracaoDesc() {
        return find("order by dataAlteracao desc").list();
    }
    
    /**
     * Busca histórico por período ordenado por data de alteração (mais recentes primeiro)
     */
    public List<HistoricoStatus> findByDataAlteracaoBetweenOrderByDataAlteracaoDesc(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return find("dataAlteracao between ?1 and ?2 order by dataAlteracao desc", dataInicio, dataFim).list();
    }
    
    /**
     * Conta histórico por ordem de serviço
     */
    public long countByOrdemServicoId(Long ordemServicoId) {
        return count("ordemServico.id", ordemServicoId);
    }
    
    /**
     * Conta histórico por status
     */
    public long countByStatus(OrdemServico.StatusOrdemServico status) {
        return count("status", status);
    }
    
    /**
     * Conta histórico por usuário
     */
    public long countByUsuarioId(Long usuarioId) {
        return count("usuario.id", usuarioId);
    }
    
    /**
     * Conta histórico por período
     */
    public long countByDataAlteracaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return count("dataAlteracao between ?1 and ?2", dataInicio, dataFim);
    }
} 