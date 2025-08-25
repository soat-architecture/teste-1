package com.grupo110.oficina.application.service;

import com.grupo110.oficina.domain.model.Veiculo;
import com.grupo110.oficina.infrastructure.repository.VeiculoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class VeiculoService {
    
    @Inject
    VeiculoRepository veiculoRepository;
    
    @Inject
    ClienteService clienteService;
    
    /**
     * Cria um novo veículo
     */
    public Veiculo criarVeiculo(@Valid Veiculo veiculo) {
        // Validar se o cliente existe e está ativo
        if (!clienteService.clienteAtivo(veiculo.getCliente().getId().longValue())) {
            throw new RuntimeException("Cliente não encontrado ou inativo");
        }
        
        // Validar se já existe veículo com a mesma placa
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("Já existe veículo com a placa: " + veiculo.getPlaca());
        }
        
        veiculoRepository.persist(veiculo);
        return veiculo;
    }
    
    /**
     * Atualiza um veículo existente
     */
    public Veiculo atualizarVeiculo(Long id, @Valid Veiculo veiculoAtualizado) {
        Veiculo veiculo = veiculoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com ID: " + id));
        
        // Validar se a placa foi alterada e se já existe outro veículo com ela
        if (!veiculo.getPlaca().equals(veiculoAtualizado.getPlaca())) {
            Optional<Veiculo> veiculoExistente = veiculoRepository.findByPlacaAndIdNot(
                    veiculoAtualizado.getPlaca(), id);
            if (veiculoExistente.isPresent()) {
                throw new RuntimeException("Já existe outro veículo com a placa: " + veiculoAtualizado.getPlaca());
            }
        }
        
        // Validar se o cliente foi alterado e se está ativo
        if (!veiculo.getCliente().getId().equals(veiculoAtualizado.getCliente().getId())) {
            if (!clienteService.clienteAtivo(veiculoAtualizado.getCliente().getId())) {
                throw new RuntimeException("Cliente não encontrado ou inativo");
            }
        }
        
        // Atualizar campos
        veiculo.setPlaca(veiculoAtualizado.getPlaca());
        veiculo.setMarca(veiculoAtualizado.getMarca());
        veiculo.setModelo(veiculoAtualizado.getModelo());
        veiculo.setAno(veiculoAtualizado.getAno());
        veiculo.setCor(veiculoAtualizado.getCor());
        veiculo.setChassi(veiculoAtualizado.getChassi());
        veiculo.setRenavam(veiculoAtualizado.getRenavam());
        veiculo.setQuilometragem(veiculoAtualizado.getQuilometragem());
        veiculo.setCombustivel(veiculoAtualizado.getCombustivel());
        veiculo.setObservacoes(veiculoAtualizado.getObservacoes());
        veiculo.setAtivo(veiculoAtualizado.getAtivo());
        veiculo.setCliente(veiculoAtualizado.getCliente());
        
        veiculoRepository.persist(veiculo);
        return veiculo;
    }
    
    /**
     * Busca veículo por ID
     */
    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com ID: " + id));
    }
    
    /**
     * Busca veículo por placa
     */
    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com placa: " + placa));
    }
    
    /**
     * Lista todos os veículos
     */
    public List<Veiculo> listarTodos() {
        return veiculoRepository.listAll();
    }
    
    /**
     * Lista veículos ativos
     */
    public List<Veiculo> listarAtivos() {
        return veiculoRepository.findAtivos();
    }
    
    /**
     * Lista veículos por cliente
     */
    public List<Veiculo> listarPorCliente(Long clienteId) {
        return veiculoRepository.findByClienteIdAndAtivoTrue(clienteId);
    }
    
    /**
     * Lista veículos por marca
     */
    public List<Veiculo> listarPorMarca(String marca) {
        return veiculoRepository.findByMarca(marca);
    }
    
    /**
     * Lista veículos por modelo
     */
    public List<Veiculo> listarPorModelo(String modelo) {
        return veiculoRepository.findByModelo(modelo);
    }
    
    /**
     * Lista veículos por ano
     */
    public List<Veiculo> listarPorAno(Integer ano) {
        return veiculoRepository.findByAno(ano);
    }
    
    /**
     * Lista veículos por marca e modelo
     */
    public List<Veiculo> listarPorMarcaEModelo(String marca, String modelo) {
        return veiculoRepository.findByMarcaAndModelo(marca, modelo);
    }
    
    /**
     * Lista veículos por tipo de combustível
     */
    public List<Veiculo> listarPorCombustivel(Veiculo.TipoCombustivel combustivel) {
        return veiculoRepository.findByCombustivel(combustivel);
    }
    
    /**
     * Lista veículos com quilometragem maior que
     */
    public List<Veiculo> listarPorQuilometragemMaiorQue(Long quilometragem) {
        return veiculoRepository.findByQuilometragemGreaterThan(quilometragem);
    }
    
    /**
     * Desativa um veículo
     */
    public Veiculo desativarVeiculo(Long id) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setAtivo(false);
        veiculoRepository.persist(veiculo);
        return veiculo;
    }
    
    /**
     * Ativa um veículo
     */
    public Veiculo ativarVeiculo(Long id) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setAtivo(true);
        veiculoRepository.persist(veiculo);
        return veiculo;
    }
    
    /**
     * Remove um veículo (soft delete)
     */
    public void removerVeiculo(Long id) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setAtivo(false);
        veiculoRepository.persist(veiculo);
    }
    
    /**
     * Atualiza quilometragem do veículo
     */
    public Veiculo atualizarQuilometragem(Long id, Long novaQuilometragem) {
        if (novaQuilometragem == null || novaQuilometragem < 0) {
            throw new RuntimeException("Quilometragem deve ser um valor positivo");
        }
        
        Veiculo veiculo = buscarPorId(id);
        veiculo.atualizarQuilometragem(novaQuilometragem);
        veiculoRepository.persist(veiculo);
        return veiculo;
    }
    
    /**
     * Verifica se veículo existe
     */
    public boolean veiculoExiste(Long id) {
        return veiculoRepository.findByIdOptional(id).isPresent();
    }
    
    /**
     * Verifica se veículo está ativo
     */
    public boolean veiculoAtivo(Long id) {
        Optional<Veiculo> veiculo = veiculoRepository.findByIdOptional(id);
        return veiculo.isPresent() && veiculo.get().getAtivo();
    }
    
    /**
     * Verifica se placa existe
     */
    public boolean placaExiste(String placa) {
        return veiculoRepository.existsByPlaca(placa);
    }
} 