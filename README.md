# Sistema de Oficina Mecânica

Sistema completo para gerenciamento de oficina mecânica desenvolvido com Quarkus e MySQL.

## 🚀 Tecnologias Utilizadas

- **Backend**: Quarkus 3.25.2
- **Banco de Dados**: MySQL 8.0
- **ORM**: Hibernate ORM com Panache
- **Migrações**: Flyway
- **Segurança**: JWT (JSON Web Tokens)
- **Cache**: Redis
- **Containerização**: Docker & Docker Compose
- **Logging**: Logback com formatação legível

## 📋 Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose
- Git

## 🛠️ Configuração e Execução

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd oficina
```

### 2. Executar com Docker Compose (Recomendado)
```bash
# Construir e executar todos os serviços
docker compose up --build

# Executar em background
docker compose up -d --build

# Parar todos os serviços
docker compose down

# Parar e remover volumes
docker compose down -v
```

### 3. Executar localmente (Desenvolvimento)
```bash
# Compilar o projeto
./mvnw clean compile

# Executar em modo desenvolvimento
./mvnw quarkus:dev

# Executar testes
./mvnw test

# Construir JAR executável
./mvnw clean package
```

## 🗄️ Serviços Disponíveis

| Serviço | Porta | Descrição |
|---------|-------|-----------|
| **Aplicação Quarkus** | 8080 | API REST principal |
| **MySQL** | 3306 | Banco de dados |
| **phpMyAdmin** | 5050 | Interface web para MySQL |
| **Redis** | 6379 | Cache e sessões |

## 🔐 Acesso ao Sistema

### Credenciais do Banco
- **Host**: localhost:3306
- **Database**: oficina_db
- **Usuário**: oficina_user
- **Senha**: oficina_pass
- **Root**: root123

### Usuário Padrão da Aplicação
- **Username**: admin
- **Senha**: admin123
- **Perfil**: Administrador

## 📊 Estrutura do Banco

### Tabelas Principais
- **usuarios**: Usuários do sistema
- **perfis**: Perfis de acesso
- **permissoes**: Permissões do sistema
- **clientes**: Cadastro de clientes
- **veiculos**: Veículos dos clientes
- **servicos**: Catálogo de serviços
- **pecas**: Estoque de peças
- **ordens_servico**: Ordens de serviço
- **historico_status**: Histórico de alterações

## 🔧 Configurações

### Logging
Os logs estão configurados para serem legíveis e organizados:

### Qualidade de Código e Análise de Vulnerabilidades
O projeto inclui integração com SonarQube para análise estática de código e detecção de vulnerabilidades:

```bash
# Executar análise local
./run-sonar-analysis.sh

# Ou usar Docker Compose para SonarQube local (PostgreSQL)
docker compose -f docker-compose.sonar.yml up -d

# Executar análise manual
mvn clean test jacoco:report
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
```

**Documentação completa**: [SONARQUBE_README.md](SONARQUBE_README.md)

**Recursos de análise**:
- ✅ Detecção de vulnerabilidades de segurança
- ✅ Análise de qualidade de código
- ✅ Relatórios de cobertura de testes
- ✅ Identificação de code smells
- ✅ Análise de duplicação de código
- Formato: `HH:mm:ss.SSS [thread] LEVEL logger - message`
- Arquivo: `logs/oficina.log`
- Console: Formatação colorida e organizada

### Flyway
- Migrações automáticas na inicialização
- Baseline automático para bancos existentes
- Scripts em `src/main/resources/db/migration/`

## 🚨 Solução de Problemas

### Erro de Conexão com MySQL
```bash
# Verificar se o container está rodando
docker compose ps

# Ver logs do MySQL
docker compose logs mysql

# Reiniciar serviços
docker compose restart
```

### Limpar Ambiente
```bash
# Remover todos os containers, volumes e imagens
docker compose down -v --remove-orphans
docker system prune -f
```

## 📝 Desenvolvimento

### Adicionar Nova Migração
```bash
# Criar arquivo: V3__Nova_Migracao.sql
# Colocar em: src/main/resources/db/migration/
```

### Estrutura de Pacotes
```
src/main/java/com/grupo110/oficina/
├── application/          # DTOs e Services
├── domain/              # Modelos de domínio
├── infrastructure/      # Repositórios e configurações
└── interfaces/          # Controllers REST
```

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

## 👥 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📞 Suporte

Para dúvidas ou problemas, abra uma [issue](issues) no repositório.
