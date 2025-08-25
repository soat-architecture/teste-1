# SonarQube - Análise de Qualidade e Vulnerabilidades

Este documento descreve como configurar e usar o SonarQube para análise de qualidade de código e detecção de vulnerabilidades no projeto Oficina.

## 🎯 Objetivos

- Análise estática de código Java
- Detecção de vulnerabilidades de segurança
- Medição de qualidade de código
- Relatórios de cobertura de testes
- Identificação de code smells e bugs potenciais

## 🚀 Configuração Rápida

### 1. Iniciar o SonarQube Localmente

```bash
# Usar o Docker Compose fornecido (PostgreSQL)
docker compose -f docker-compose.sonar.yml up -d

# Aguardar o serviço estar disponível (pode levar alguns minutos)
# Acesse: http://localhost:9000
# Login padrão: admin/admin
# Banco: PostgreSQL 15 (porta 5432)
```

**Nota**: O SonarQube Community Edition suporta apenas PostgreSQL, H2 e Oracle. MySQL é suportado apenas nas versões Developer/Enterprise.

### 2. Executar Análise

```bash
# Usar o script automatizado
./run-sonar-analysis.sh

# Ou executar manualmente
mvn clean test jacoco:report
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
```

## 📋 Pré-requisitos

- Java 21+
- Maven 3.6+
- Docker e Docker Compose (para execução local)
- Acesso ao SonarQube (local ou remoto)

## ⚙️ Configurações

### Arquivo `sonar-project.properties`

O arquivo contém configurações específicas para:
- Análise de vulnerabilidades Java
- Configurações de cobertura de código
- Exclusões de arquivos
- Configurações de qualidade

### Plugin Maven

O plugin `sonar-maven-plugin` foi adicionado ao `pom.xml` para integração com Maven.

## 🔍 Tipos de Análise

### 1. Vulnerabilidades de Segurança
- SQL Injection
- Cross-Site Scripting (XSS)
- Injeção de código
- Exposição de dados sensíveis

### 2. Qualidade de Código
- Code smells
- Duplicação de código
- Complexidade ciclomática
- Padrões de código

### 3. Cobertura de Testes
- Relatórios JaCoCo
- Métricas de cobertura
- Análise de branches não testados

## 📊 Relatórios Disponíveis

### Dashboard SonarQube
- Visão geral da qualidade
- Métricas de segurança
- Histórico de análises
- Comparação entre branches

### Relatórios Locais
- Cobertura de código: `target/site/jacoco/index.html`
- Relatórios Maven: `target/site/`

## 🛠️ Comandos Úteis

### Análise Completa
```bash
mvn clean test jacoco:report sonar:sonar
```

### Apenas Análise (sem testes)
```bash
mvn sonar:sonar
```

### Análise com Quality Gate
```bash
mvn sonar:sonar -Dsonar.qualitygate.wait=true
```

## 🔧 Configurações Avançadas

### Variáveis de Ambiente
```bash
export SONAR_HOST_URL="http://localhost:9000"
export SONAR_LOGIN="seu_usuario"
export SONAR_PASSWORD="sua_senha"
export SONAR_TOKEN="seu_token"
```

### Configurações Personalizadas
```bash
mvn sonar:sonar \
    -Dsonar.host.url=http://localhost:9000 \
    -Dsonar.login=admin \
    -Dsonar.password=admin \
    -Dsonar.projectKey=com.grupo110:oficina \
    -Dsonar.projectName="Oficina - Sistema de Gestão" \
    -Dsonar.projectVersion=1.0.0-SNAPSHOT \
    -Dsonar.qualitygate.wait=true \
    -Dsonar.qualitygate.timeout=300
```

## 🚨 Troubleshooting

### Problemas Comuns

1. **SonarQube não acessível**
   - Verificar se o container está rodando: `docker ps`
   - Verificar logs: `docker logs sonarqube`

2. **Erro de autenticação**
   - Verificar credenciais no arquivo de configuração
   - Resetar senha padrão se necessário

3. **Problemas com PostgreSQL**
   - Verificar se o PostgreSQL está rodando: `docker logs sonarqube-db`
   - Verificar conexão: `docker exec -it sonarqube-db psql -U sonar -d sonarqube`
   - Resetar container se necessário: `docker compose -f docker-compose.sonar.yml down -v && docker compose -f docker-compose.sonar.yml up -d`

4. **Análise falha**
   - Verificar se os testes passam: `mvn test`
   - Verificar se o JaCoCo gerou relatórios
   - Verificar logs do Maven

### Logs Úteis
```bash
# Logs do SonarQube
docker logs sonarqube

# Logs do Maven (verbose)
mvn sonar:sonar -X

# Status do SonarQube
curl -u admin:admin http://localhost:9000/api/system/status
```

## 📚 Recursos Adicionais

- [Documentação oficial do SonarQube](https://docs.sonarqube.org/)
- [Plugin Maven do SonarQube](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/)
- [Regras de qualidade Java](https://rules.sonarsource.com/java/)
- [Métricas de qualidade](https://docs.sonarqube.org/latest/user-guide/metric-definitions/)

## 🤝 Contribuição

Para melhorar a configuração do SonarQube:
1. Atualizar regras de qualidade
2. Ajustar thresholds de métricas
3. Adicionar novos plugins
4. Melhorar configurações de exclusão

## 📞 Suporte

Em caso de problemas:
1. Verificar logs e documentação
2. Consultar issues do projeto
3. Abrir nova issue com detalhes do problema 