package com.grupo110.oficina.application.service;

import com.grupo110.oficina.domain.model.Cliente;
import com.grupo110.oficina.infrastructure.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ClienteService {
    
    @Inject
    ClienteRepository clienteRepository;
    
    /**
     * Cria um novo cliente
     */
    public Cliente criarCliente(@Valid Cliente cliente) {
        // Validar se já existe cliente com o mesmo documento
        if (clienteRepository.existsByDocumento(cliente.getDocumento())) {
            throw new RuntimeException("Já existe cliente com o documento: " + cliente.getDocumento());
        }
        
        // Validar se já existe cliente com o mesmo email (se informado)
        if (cliente.getEmail() != null && clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Já existe cliente com o email: " + cliente.getEmail());
        }
        
        clienteRepository.persist(cliente);
        return cliente;
    }
    
    /**
     * Atualiza um cliente existente
     */
    public Cliente atualizarCliente(Long id, @Valid Cliente clienteAtualizado) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        
        // Validar se o documento foi alterado e se já existe outro cliente com ele
        if (!cliente.getDocumento().equals(clienteAtualizado.getDocumento())) {
            Optional<Cliente> clienteExistente = clienteRepository.findByDocumentoAndIdNot(
                    clienteAtualizado.getDocumento(), id);
            if (clienteExistente.isPresent()) {
                throw new RuntimeException("Já existe outro cliente com o documento: " + clienteAtualizado.getDocumento());
            }
        }
        
        // Validar se o email foi alterado e se já existe outro cliente com ele
        if (clienteAtualizado.getEmail() != null && 
            !clienteAtualizado.getEmail().equals(cliente.getEmail())) {
            Optional<Cliente> clienteExistente = clienteRepository.findByEmailAndIdNot(
                    clienteAtualizado.getEmail(), id);
            if (clienteExistente.isPresent()) {
                throw new RuntimeException("Já existe outro cliente com o email: " + clienteAtualizado.getEmail());
            }
        }
        
        // Atualizar campos
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setDocumento(clienteAtualizado.getDocumento());
        cliente.setTipoDocumento(clienteAtualizado.getTipoDocumento());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());
        cliente.setCidade(clienteAtualizado.getCidade());
        cliente.setEstado(clienteAtualizado.getEstado());
        cliente.setCep(clienteAtualizado.getCep());
        cliente.setAtivo(clienteAtualizado.getAtivo());
        
        clienteRepository.persist(cliente);
        return cliente;
    }
    
    /**
     * Busca cliente por ID
     */
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }
    
    /**
     * Busca cliente por documento
     */
    public Cliente buscarPorDocumento(String documento) {
        return clienteRepository.findByDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com documento: " + documento));
    }
    
    /**
     * Lista todos os clientes
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.listAll();
    }
    
    /**
     * Lista clientes ativos
     */
    public List<Cliente> listarAtivos() {
        return clienteRepository.findAtivos();
    }
    
    /**
     * Lista clientes por nome (busca parcial)
     */
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Lista clientes por tipo de documento
     */
    public List<Cliente> buscarPorTipoDocumento(Cliente.TipoDocumento tipoDocumento) {
        return clienteRepository.findByTipoDocumento(tipoDocumento);
    }
    
    /**
     * Desativa um cliente
     */
    public Cliente desativarCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        clienteRepository.persist(cliente);
        return cliente;
    }
    
    /**
     * Ativa um cliente
     */
    public Cliente ativarCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(true);
        clienteRepository.persist(cliente);
        return cliente;
    }
    
    /**
     * Remove um cliente (soft delete)
     */
    public void removerCliente(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        clienteRepository.persist(cliente);
    }
    
    /**
     * Verifica se cliente existe
     */
    public boolean clienteExiste(Long id) {
        return clienteRepository.findByIdOptional(id).isPresent();
    }
    
    /**
     * Verifica se cliente está ativo
     */
    public boolean clienteAtivo(Long id) {
        Optional<Cliente> cliente = clienteRepository.findByIdOptional(id);
        return cliente.isPresent() && cliente.get().getAtivo();
    }
} 