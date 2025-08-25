package com.grupo110.oficina.application.service;

import com.grupo110.oficina.domain.model.Usuario;
import com.grupo110.oficina.infrastructure.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class UsuarioService {
    
    @Inject
    UsuarioRepository usuarioRepository;
    
    /**
     * Cria um novo usuário
     */
    public Usuario criarUsuario(@Valid Usuario usuario) {
        // Validar se já existe usuário com o mesmo username
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Já existe usuário com o username: " + usuario.getUsername());
        }
        
        // Validar se já existe usuário com o mesmo email
        if (usuario.getEmail() != null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe usuário com o email: " + usuario.getEmail());
        }
        
        // Validar se a senha não é nula ou vazia
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }
        
        // Definir data de cadastro
        usuario.setDataCadastro(LocalDateTime.now());
        
        // Definir último acesso como data de cadastro
        usuario.setUltimoAcesso(LocalDateTime.now());
        
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Atualiza um usuário existente
     */
    public Usuario atualizarUsuario(Long id, @Valid Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        // Validar se o username foi alterado e se já existe outro usuário com ele
        if (!usuario.getUsername().equals(usuarioAtualizado.getUsername())) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByUsernameAndIdNot(
                    usuarioAtualizado.getUsername(), id);
            if (usuarioExistente.isPresent()) {
                throw new RuntimeException("Já existe outro usuário com o username: " + usuarioAtualizado.getUsername());
            }
        }
        
        // Validar se o email foi alterado e se já existe outro usuário com ele
        if (usuarioAtualizado.getEmail() != null && 
            !usuarioAtualizado.getEmail().equals(usuario.getEmail())) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmailAndIdNot(
                    usuarioAtualizado.getEmail(), id);
            if (usuarioExistente.isPresent()) {
                throw new RuntimeException("Já existe outro usuário com o email: " + usuarioAtualizado.getEmail());
            }
        }
        
        // Atualizar campos permitidos
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setUsername(usuarioAtualizado.getUsername());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setAtivo(usuarioAtualizado.getAtivo());
        
        // Atualizar senha apenas se foi fornecida
        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().trim().isEmpty()) {
            usuario.setSenha(usuarioAtualizado.getSenha());
        }
        
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Busca usuário por ID
     */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }
    
    /**
     * Busca usuário por username
     */
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com username: " + username));
    }
    
    /**
     * Busca usuário por email
     */
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
    }
    
    /**
     * Lista todos os usuários
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.listAll();
    }
    
    /**
     * Lista usuários ativos
     */
    public List<Usuario> listarAtivos() {
        return usuarioRepository.findAtivos();
    }
    
    /**
     * Lista usuários por nome (busca parcial)
     */
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Lista usuários por último acesso
     */
    public List<Usuario> listarPorUltimoAcesso(LocalDateTime data) {
        return usuarioRepository.findByUltimoAcessoBefore(data);
    }
    
    /**
     * Lista usuários por período de data de cadastro
     */
    public List<Usuario> listarPorPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return usuarioRepository.findByDataCadastroBetween(dataInicio, dataFim);
    }
    
    /**
     * Lista usuários ordenados por nome
     */
    public List<Usuario> listarOrdenadosPorNome() {
        return usuarioRepository.findOrderByNome();
    }
    
    /**
     * Lista usuários ordenados por data de cadastro (mais recentes primeiro)
     */
    public List<Usuario> listarOrdenadosPorDataCadastro() {
        return usuarioRepository.findOrderByDataCadastroDesc();
    }
    
    /**
     * Lista usuários ordenados por último acesso (mais recentes primeiro)
     */
    public List<Usuario> listarOrdenadosPorUltimoAcesso() {
        return usuarioRepository.findOrderByUltimoAcessoDesc();
    }
    
    /**
     * Lista usuários que não acessaram há mais de X dias
     */
    public List<Usuario> listarUsuariosInativos(int diasLimite) {
        return usuarioRepository.findUsuariosInativos(diasLimite);
    }
    
    /**
     * Lista usuários por perfil
     */
    public List<Usuario> listarPorPerfil(String nomePerfil) {
        return usuarioRepository.findByPerfisNome(nomePerfil);
    }
    
    /**
     * Lista usuários por tipo de perfil
     */
    public List<Usuario> listarPorTipoPerfil(String tipoPerfil) {
        return usuarioRepository.findByPerfisTipo(tipoPerfil);
    }
    
    /**
     * Desativa um usuário
     */
    public Usuario desativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Ativa um usuário
     */
    public Usuario ativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(true);
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Remove um usuário (soft delete)
     */
    public Usuario removerUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Atualiza último acesso do usuário
     */
    public Usuario atualizarUltimoAcesso(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Altera senha do usuário
     */
    public Usuario alterarSenha(Long id, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new RuntimeException("Nova senha é obrigatória");
        }
        
        Usuario usuario = buscarPorId(id);
        usuario.setSenha(novaSenha);
        usuarioRepository.persist(usuario);
        return usuario;
    }

    /**
     * Adiciona perfil ao usuário
     */
    public Usuario adicionarPerfil(Long id, com.grupo110.oficina.domain.model.Perfil perfil) {
        Usuario usuario = buscarPorId(id);
        usuario.setPerfil(perfil);
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Remove perfil do usuário
     */
    public Usuario removerPerfil(Long id, com.grupo110.oficina.domain.model.Perfil perfil) {
        Usuario usuario = buscarPorId(id);
        usuario.setPerfil(null);
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    /**
     * Verifica se usuário existe
     */
    public boolean usuarioExiste(Long id) {
        return usuarioRepository.findByIdOptional(id).isPresent();
    }
    
    /**
     * Verifica se usuário está ativo
     */
    public boolean usuarioAtivo(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findByIdOptional(id);
        return usuario.isPresent() && usuario.get().getAtivo();
    }
    
    /**
     * Verifica se username existe
     */
    public boolean usernameExiste(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    /**
     * Verifica se email existe
     */
    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    /**
     * Conta usuários ativos
     */
    public long contarAtivos() {
        return usuarioRepository.countByAtivoTrue();
    }
    
    /**
     * Conta usuários por período de cadastro
     */
    public long contarPorPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return usuarioRepository.countByDataCadastroBetween(dataInicio, dataFim);
    }
    
    /**
     * Autentica usuário por username e senha
     */
    public Usuario autenticar(String username, String password) {
        try {
            Usuario usuario = buscarPorUsername(username);
            
            // Verificar se usuário está ativo
            if (!usuario.getAtivo()) {
                throw new RuntimeException("Usuário inativo");
            }
            
            // Verificar senha (implementação básica - em produção usar hash)
            if (!usuario.getSenha().equals(password)) {
                throw new RuntimeException("Senha incorreta");
            }
            
            // Atualizar último acesso
            usuario.setUltimoAcesso(LocalDateTime.now());
            usuarioRepository.persist(usuario);
            return usuario;
            
        } catch (Exception e) {
            throw new RuntimeException("Falha na autenticação: " + e.getMessage());
        }
    }
    
    /**
     * Verifica se usuário tem perfil específico
     */
    public boolean usuarioTemPerfil(Long id, String nomePerfil) {
        Usuario usuario = buscarPorId(id);
        return usuario.getPerfil().getNome().equals(nomePerfil);
    }
    
    /**
     * Verifica se usuário tem tipo de perfil específico
     */
    public boolean usuarioTemTipoPerfil(Long id, String tipoPerfil) {
        Usuario usuario = buscarPorId(id);
        return usuario.getPerfil().getTipo().toString().equals(tipoPerfil);
    }
} 