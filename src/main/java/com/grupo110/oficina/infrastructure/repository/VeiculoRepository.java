package com.grupo110.oficina.infrastructure.repository;

import com.grupo110.oficina.domain.model.Veiculo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VeiculoRepository implements PanacheRepository<Veiculo> {
    
    /**
     * Busca veículo por placa
     */
    public Optional<Veiculo> findByPlaca(String placa) {
        return find("placa", placa).firstResultOptional();
    }
    
    /**
     * Busca veículos por cliente
     */
    public List<Veiculo> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).list();
    }
    
    /**
     * Busca veículos por marca
     */
    public List<Veiculo> findByMarca(String marca) {
        return find("marca", marca).list();
    }
    
    /**
     * Busca veículos por modelo
     */
    public List<Veiculo> findByModelo(String modelo) {
        return find("modelo", modelo).list();
    }
    
    /**
     * Busca veículos por ano
     */
    public List<Veiculo> findByAno(Integer ano) {
        return find("ano", ano).list();
    }
    
    /**
     * Busca veículos por marca e modelo
     */
    public List<Veiculo> findByMarcaAndModelo(String marca, String modelo) {
        return find("marca = ?1 and modelo = ?2", marca, modelo).list();
    }
    
    /**
     * Busca veículos ativos
     */
    public List<Veiculo> findAtivos() {
        return find("ativo", true).list();
    }
    
    /**
     * Busca veículos por cliente ativos
     */
    public List<Veiculo> findByClienteIdAndAtivoTrue(Long clienteId) {
        return find("cliente.id = ?1 and ativo = ?2", clienteId, true).list();
    }
    
    /**
     * Verifica se existe veículo com a placa informada
     */
    public boolean existsByPlaca(String placa) {
        return count("placa", placa) > 0;
    }
    
    /**
     * Busca veículo por placa ignorando o ID (para validação de unicidade em updates)
     */
    public Optional<Veiculo> findByPlacaAndIdNot(String placa, Long id) {
        return find("placa = ?1 and id != ?2", placa, id).firstResultOptional();
    }
    
    /**
     * Busca veículos por quilometragem maior que o valor informado
     */
    public List<Veiculo> findByQuilometragemGreaterThan(Long quilometragem) {
        return find("quilometragem > ?1", quilometragem).list();
    }
    
    /**
     * Busca veículos por tipo de combustível
     */
    public List<Veiculo> findByCombustivel(Veiculo.TipoCombustivel combustivel) {
        return find("combustivel", combustivel).list();
    }
} 