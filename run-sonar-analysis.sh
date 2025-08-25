#!/bin/bash

# Script para executar análise do SonarQube
# Projeto: Oficina - Sistema de Gestão de Oficina Mecânica

echo "🚀 Iniciando análise do SonarQube..."
echo "📁 Projeto: Oficina"
echo "🗄️ Banco: PostgreSQL (SonarQube Community Edition)"
echo "🔍 Verificando dependências..."

# Verificar se o Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale o Maven primeiro."
    exit 1
fi

# Verificar se o Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale o Java primeiro."
    exit 1
fi

echo "✅ Dependências verificadas"
echo "🧹 Limpando build anterior..."

# Limpar build anterior
mvn clean

echo "🧪 Executando testes e gerando relatório de cobertura..."

# Executar testes e gerar relatório de cobertura
mvn test jacoco:report

echo "🔍 Executando análise do SonarQube..."

# Executar análise do SonarQube
# Você pode configurar as variáveis de ambiente conforme necessário
export SONAR_HOST_URL="http://localhost:9000"
export SONAR_LOGIN="admin"  # Usuário padrão do SonarQube
export SONAR_PASSWORD="123456"  # Nova senha configurada

# Executar análise
mvn sonar:sonar \
    -Dsonar.host.url=$SONAR_HOST_URL \
    -Dsonar.login=$SONAR_LOGIN \
    -Dsonar.password=$SONAR_PASSWORD \
    -Dsonar.projectKey=com.grupo110:oficina \
    -Dsonar.projectName="Oficina - Sistema de Gestão" \
    -Dsonar.projectVersion=1.0.0-SNAPSHOT

echo "✅ Análise do SonarQube concluída!"
echo "🌐 Acesse o dashboard em: $SONAR_HOST_URL"
echo "🔑 Login: $SONAR_LOGIN"
echo "🔑 Senha: $SONAR_PASSWORD"
echo ""
echo "📊 Relatórios disponíveis:"
echo "   - Cobertura de código: target/site/jacoco/index.html"
echo "   - Relatório do SonarQube: $SONAR_HOST_URL" 