-- Script para inserir dados iniciais do sistema
-- V2__Insert_Initial_Data.sql - MySQL

-- Inserir perfis primeiro
INSERT INTO perfis (nome, descricao, tipo, ativo) VALUES
('Administrador', 'Perfil com acesso total ao sistema', 'ADMIN', true),
('Gerente', 'Perfil com acesso gerencial ao sistema', 'GERENTE', true),
('Mecânico', 'Perfil para mecânicos executarem serviços', 'MECANICO', true),
('Atendente', 'Perfil para atendentes atenderem clientes', 'ATENDENTE', true),
('Cliente', 'Perfil para clientes consultarem suas ordens', 'CLIENTE', true);

-- Inserir permissões básicas
INSERT INTO permissoes (nome, descricao, tipo, recurso, acao) VALUES
('CRUD_CLIENTE', 'Permissão para criar, ler, atualizar e deletar clientes', 'CRUD_CLIENTE', 'cliente', 'crud'),
('CRUD_VEICULO', 'Permissão para criar, ler, atualizar e deletar veículos', 'CRUD_VEICULO', 'veiculo', 'crud'),
('CRUD_SERVICO', 'Permissão para criar, ler, atualizar e deletar serviços', 'CRUD_SERVICO', 'servico', 'crud'),
('CRUD_PECA', 'Permissão para criar, ler, atualizar e deletar peças', 'CRUD_PECA', 'peca', 'crud'),
('CRUD_ORDEM_SERVICO', 'Permissão para criar, ler, atualizar e deletar ordens de serviço', 'CRUD_ORDEM_SERVICO', 'ordem_servico', 'crud'),
('CRUD_USUARIO', 'Permissão para criar, ler, atualizar e deletar usuários', 'CRUD_USUARIO', 'usuario', 'crud'),
('CRUD_PERFIL', 'Permissão para criar, ler, atualizar e deletar perfis', 'CRUD_PERFIL', 'perfil', 'crud'),
('CRUD_PERMISSAO', 'Permissão para criar, ler, atualizar e deletar permissões', 'CRUD_PERMISSAO', 'permissao', 'crud'),
('VISUALIZAR_RELATORIOS', 'Permissão para visualizar relatórios do sistema', 'VISUALIZAR_RELATORIOS', 'relatorio', 'visualizar'),
('GERENCIAR_ESTOQUE', 'Permissão para gerenciar estoque de peças', 'GERENCIAR_ESTOQUE', 'estoque', 'gerenciar'),
('APROVAR_ORDENS', 'Permissão para aprovar ordens de serviço', 'APROVAR_ORDENS', 'ordem_servico', 'aprovar'),
('EXECUTAR_SERVICOS', 'Permissão para executar serviços', 'EXECUTAR_SERVICOS', 'servico', 'executar'),
('ATENDER_CLIENTES', 'Permissão para atender clientes', 'ATENDER_CLIENTES', 'cliente', 'atender');

-- Associar permissões aos perfis
-- Administrador: todas as permissões
INSERT INTO perfil_permissoes (perfil_id, permissao_id)
SELECT p.id, perm.id
FROM perfis p, permissoes perm
WHERE p.nome = 'Administrador';

-- Gerente: permissões gerenciais
INSERT INTO perfil_permissoes (perfil_id, permissao_id)
SELECT p.id, perm.id
FROM perfis p, permissoes perm
WHERE p.nome = 'Gerente' 
AND perm.tipo IN ('CRUD_CLIENTE', 'CRUD_VEICULO', 'CRUD_SERVICO', 'CRUD_PECA', 'CRUD_ORDEM_SERVICO', 'VISUALIZAR_RELATORIOS', 'GERENCIAR_ESTOQUE', 'APROVAR_ORDENS');

-- Mecânico: permissões para executar serviços
INSERT INTO perfil_permissoes (perfil_id, permissao_id)
SELECT p.id, perm.id
FROM perfis p, permissoes perm
WHERE p.nome = 'Mecânico' 
AND perm.tipo IN ('CRUD_ORDEM_SERVICO', 'EXECUTAR_SERVICOS', 'GERENCIAR_ESTOQUE');

-- Atendente: permissões para atender clientes
INSERT INTO perfil_permissoes (perfil_id, permissao_id)
SELECT p.id, perm.id
FROM perfis p, permissoes perm
WHERE p.nome = 'Atendente' 
AND perm.tipo IN ('CRUD_CLIENTE', 'CRUD_VEICULO', 'CRUD_ORDEM_SERVICO', 'ATENDER_CLIENTES');

-- Cliente: permissões limitadas
INSERT INTO perfil_permissoes (perfil_id, permissao_id)
SELECT p.id, perm.id
FROM perfis p, permissoes perm
WHERE p.nome = 'Cliente' 
AND perm.tipo IN ('CRUD_ORDEM_SERVICO');

-- Inserir usuário administrador padrão
-- Senha: admin123 (deve ser criptografada em produção)
INSERT INTO usuarios (nome, username, email, senha, ativo, perfil_id) VALUES
('Administrador do Sistema', 'admin', 'admin@oficina.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', true, 
 (SELECT id FROM perfis WHERE nome = 'Administrador'));

-- Inserir alguns serviços básicos
INSERT INTO servicos (nome, descricao, valor_base, tempo_medio_execucao, categoria) VALUES
('Troca de Óleo', 'Troca de óleo do motor e filtro de óleo', 45.00, 30, 'MECANICA'),
('Alinhamento', 'Alinhamento das rodas dianteiras', 35.00, 45, 'SUSPENSAO'),
('Troca de Filtro de Ar', 'Substituição do filtro de ar do motor', 25.00, 15, 'MECANICA'),
('Troca de Freios', 'Substituição das pastilhas de freio dianteiras', 80.00, 60, 'FREIOS'),
('Diagnóstico Elétrico', 'Verificação do sistema elétrico do veículo', 50.00, 45, 'ELETRICA'),
('Lavagem e Aspiração', 'Lavagem externa e aspiração interna', 30.00, 30, 'OUTROS');

-- Inserir algumas peças básicas
INSERT INTO pecas (nome, codigo, descricao, fabricante, preco_custo, preco_venda, quantidade_estoque, categoria) VALUES
('Óleo de Motor 5W30', 'OLEO001', 'Óleo de motor sintético 5W30 1L', 'Mobil', 15.00, 25.00, 50, 'LUBRIFICANTES'),
('Filtro de Óleo', 'FILTRO001', 'Filtro de óleo para motores 1.0 a 2.0', 'Fram', 8.00, 15.00, 30, 'FILTROS'),
('Filtro de Ar', 'FILTRO002', 'Filtro de ar para motores 1.0 a 2.0', 'Fram', 12.00, 20.00, 25, 'FILTROS'),
('Pastilha de Freio Dianteira', 'FREIO001', 'Pastilha de freio para dianteira', 'Fras-le', 25.00, 45.00, 20, 'FREIOS'),
('Fluido de Freio DOT4', 'FLUIDO001', 'Fluido de freio DOT4 500ml', 'Castrol', 8.00, 15.00, 40, 'FREIOS');

-- Inserir clientes
INSERT INTO clientes (nome, documento, tipo_documento, email, telefone, endereco, cidade, estado, cep, ativo) VALUES
('João Silva Santos', '12345678901', 'CPF', 'joao.silva@email.com', '(11) 99999-1111', 'Rua das Flores, 123 - Bairro Centro', 'São Paulo', 'SP', '01234-567', true),
('Maria Oliveira Costa', '98765432100', 'CPF', 'maria.oliveira@email.com', '(11) 88888-2222', 'Av. Paulista, 456 - Bairro Bela Vista', 'São Paulo', 'SP', '01345-678', true),
('Carlos Eduardo Pereira', '45678912345', 'CPF', 'carlos.pereira@email.com', '(11) 77777-3333', 'Rua Augusta, 789 - Bairro Consolação', 'São Paulo', 'SP', '01456-789', true),
('Empresa ABC Ltda', '12345678000190', 'CNPJ', 'contato@abc.com.br', '(11) 66666-4444', 'Rua da Indústria, 321 - Bairro Industrial', 'São Paulo', 'SP', '01567-890', true),
('Ana Beatriz Ferreira', '78912345678', 'CPF', 'ana.ferreira@email.com', '(11) 55555-5555', 'Rua das Palmeiras, 654 - Bairro Jardins', 'São Paulo', 'SP', '01678-901', true);

-- Inserir veículos (2 vinculados ao mesmo cliente - João Silva)
INSERT INTO veiculos (cliente_id, placa, marca, modelo, ano, cor, chassi, renavam, quilometragem, combustivel, observacoes, ativo) VALUES
((SELECT id FROM clientes WHERE nome = 'João Silva Santos'), 'ABC1234', 'Honda', 'Civic', 2020, 'Prata', 'HONDA1234567890123', '12345678901', 45000, 'FLEX', 'Veículo em excelente estado', true),
((SELECT id FROM clientes WHERE nome = 'João Silva Santos'), 'XYZ5678', 'Toyota', 'Corolla', 2019, 'Branco', 'TOYOTA9876543210987', '98765432109', 38000, 'FLEX', 'Segundo veículo do cliente', true),
((SELECT id FROM clientes WHERE nome = 'Maria Oliveira Costa'), 'DEF9012', 'Volkswagen', 'Golf', 2021, 'Preto', 'VW1234567890123456', '45678912345', 28000, 'FLEX', 'Veículo novo, primeira revisão', true),
((SELECT id FROM clientes WHERE nome = 'Carlos Eduardo Pereira'), 'GHI3456', 'Ford', 'Focus', 2018, 'Azul', 'FORD6543210987654321', '78912345678', 52000, 'FLEX', 'Veículo com histórico completo', true),
((SELECT id FROM clientes WHERE nome = 'Empresa ABC Ltda'), 'JKL7890', 'Chevrolet', 'Onix', 2022, 'Vermelho', 'CHEV1234567890123456', '32165498765', 15000, 'FLEX', 'Veículo da frota empresarial', true),
((SELECT id FROM clientes WHERE nome = 'Ana Beatriz Ferreira'), 'MNO1234', 'Hyundai', 'HB20', 2020, 'Cinza', 'HYUN9876543210987654', '14725836985', 35000, 'FLEX', 'Veículo bem conservado', true);
