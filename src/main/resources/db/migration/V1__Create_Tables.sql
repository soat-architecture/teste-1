-- Script de migração inicial para o sistema de oficina
-- V1__Create_Tables.sql - MySQL

-- Tabela de perfis (deve ser criada primeiro)
CREATE TABLE perfis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    tipo VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de usuários
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    ultimo_acesso TIMESTAMP NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    perfil_id BIGINT NOT NULL,
    FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE
);

-- Tabela de permissões
CREATE TABLE permissoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    tipo VARCHAR(100) NOT NULL,
    recurso VARCHAR(100),
    acao VARCHAR(100),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de relacionamento perfil-permissão
CREATE TABLE perfil_permissoes (
    perfil_id BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL,
    PRIMARY KEY (perfil_id, permissao_id),
    FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE,
    FOREIGN KEY (permissao_id) REFERENCES permissoes(id) ON DELETE CASCADE
);

-- Tabela de clientes
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    documento VARCHAR(14) NOT NULL UNIQUE,
    tipo_documento VARCHAR(5) NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(20),
    endereco TEXT,
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(10),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de veículos
CREATE TABLE veiculos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(8) NOT NULL UNIQUE,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    cor VARCHAR(50),
    chassi VARCHAR(17),
    renavam VARCHAR(11),
    quilometragem BIGINT,
    combustivel ENUM('DIESEL', 'ELETRICO', 'ETANOL', 'FLEX', 'GASOLINA', 'HIBRIDO'),
    observacoes TEXT,
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);

-- Tabela de serviços
CREATE TABLE servicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor_base DECIMAL(10,2) NOT NULL,
    tempo_medio_execucao INTEGER,
    categoria ENUM('AR_CONDICIONADO', 'ELETRICA', 'FREIOS', 'FUNILARIA', 'MECANICA', 'MOTOR', 'OUTROS', 'PINTURA', 'SUSPENSAO', 'TRANSMISSAO'),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de peças
CREATE TABLE pecas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) UNIQUE,
    descricao TEXT,
    fabricante VARCHAR(100),
    marca_veiculo VARCHAR(50),
    modelo_veiculo VARCHAR(100),
    ano_inicio INTEGER,
    ano_fim INTEGER,
    preco_custo DECIMAL(10,2) NOT NULL,
    preco_venda DECIMAL(10,2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL DEFAULT 0,
    quantidade_minima INTEGER,
    unidade_medida VARCHAR(20),
    localizacao_estoque VARCHAR(100),
    categoria VARCHAR(50),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de ordens de serviço
CREATE TABLE ordens_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_os VARCHAR(50) NOT NULL UNIQUE,
    status ENUM('AGUARDANDO_APROVACAO', 'APROVADA', 'EM_EXECUCAO', 'AGUARDANDO_PECAS', 'FINALIZADA', 'ENTREGUE', 'CANCELADA'),
    descricao_problema TEXT NOT NULL,
    diagnostico TEXT,
    observacoes TEXT,
    valor_total DECIMAL(10,2),
    valor_mao_obra DECIMAL(10,2),
    valor_pecas DECIMAL(10,2),
    prazo_entrega TIMESTAMP NULL,
    data_recebimento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio_execucao TIMESTAMP NULL,
    data_finalizacao TIMESTAMP NULL,
    data_entrega TIMESTAMP NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    cliente_id BIGINT NOT NULL,
    veiculo_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id) ON DELETE CASCADE
);

-- Tabela de itens de serviço
CREATE TABLE itens_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    observacoes TEXT,
    ordem_servico_id BIGINT NOT NULL,
    servico_id BIGINT NOT NULL,
    FOREIGN KEY (ordem_servico_id) REFERENCES ordens_servico(id) ON DELETE CASCADE,
    FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE
);

-- Tabela de itens de peça
CREATE TABLE itens_peca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    observacoes TEXT,
    ordem_servico_id BIGINT NOT NULL,
    peca_id BIGINT NOT NULL,
    FOREIGN KEY (ordem_servico_id) REFERENCES ordens_servico(id) ON DELETE CASCADE,
    FOREIGN KEY (peca_id) REFERENCES pecas(id) ON DELETE CASCADE
);

-- Tabela de histórico de status
CREATE TABLE historico_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM('AGUARDANDO_APROVACAO', 'APROVADA', 'EM_EXECUCAO', 'AGUARDANDO_PECAS', 'FINALIZADA', 'ENTREGUE', 'CANCELADA'),
    data_alteracao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacoes TEXT,
    usuario_alteracao VARCHAR(100),
    ordem_servico_id BIGINT NOT NULL,
    FOREIGN KEY (ordem_servico_id) REFERENCES ordens_servico(id) ON DELETE CASCADE
);

-- Índices para melhorar performance
CREATE INDEX idx_clientes_documento ON clientes(documento);
CREATE INDEX idx_clientes_nome ON clientes(nome);
CREATE INDEX idx_veiculos_placa ON veiculos(placa);
CREATE INDEX idx_veiculos_cliente ON veiculos(cliente_id);
CREATE INDEX idx_ordens_servico_numero ON ordens_servico(numero_os);
CREATE INDEX idx_ordens_servico_status ON ordens_servico(status);
CREATE INDEX idx_ordens_servico_cliente ON ordens_servico(cliente_id);
CREATE INDEX idx_ordens_servico_veiculo ON ordens_servico(veiculo_id);
CREATE INDEX idx_ordens_servico_data_recebimento ON ordens_servico(data_recebimento);
CREATE INDEX idx_pecas_codigo ON pecas(codigo);
CREATE INDEX idx_pecas_categoria ON pecas(categoria);
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_usuarios_email ON usuarios(email);