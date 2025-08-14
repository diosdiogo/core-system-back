# Resumo das Migrações Flyway - Core System Back

Este documento resume todas as migrações Flyway criadas para o sistema Core System Back.

## 📋 **Migrações Existentes**

### **V1 - V5: Estrutura de Endereços**
- **V1**: `create_country_table.sql` - Tabela de países
- **V2**: `create_state_table.sql` - Tabela de estados
- **V3**: `create_city_table.sql` - Tabela de cidades
- **V4**: `create_address_table.sql` - Tabela de endereços
- **V5**: `update_address_add_neighborhood_and_rename_cep.sql` - Atualizações de endereço

### **V6 - V8: Ajustes de Endereço**
- **V6**: `update_address_complement_nullable.sql` - Complemento opcional
- **V7**: `update_address_neighborhood_nullable.sql` - Bairro opcional
- **V8**: `rename_zipCode_to_zip_code.sql` - Renomear campo CEP

### **V9 - V11: Usuários e Perfis**
- **V9**: `create_table_profile.sql` - Tabela de perfis
- **V10**: `create_table_user.sql` - Tabela de usuários
- **V11**: `create_table_user_company.sql` - Relacionamento usuário-empresa

### **V12 - V13: Empresas**
- **V12**: `create_table_company.sql` - Tabela de empresas
- **V13**: `insert_sample_companies.sql` - Dados de exemplo de empresas

### **V14 - V20: Aplicações, Usuários Responsáveis, Status de Empresas, Cargo de Usuários e Relacionamentos App-Empresa**
- **V14**: `create_table_apps.sql` - Tabela de aplicações
- **V15**: `create_table_user_responsible.sql` - Tabela de usuários responsáveis
- **V16**: `create_country_table.sql` - Tabela de países
- **V17**: `add_status_column_to_company.sql` - Adiciona coluna status na tabela company
- **V18**: `add_cargo_column_to_user.sql` - Adiciona coluna cargo na tabela user
- **V19**: `create_table_app_empresa.sql` - Tabela de relacionamento entre aplicações e empresas
- **V20**: `insert_sample_app_empresa.sql` - Dados de exemplo para relacionamentos app-empresa

## 🏗️ **Estrutura Completa do Banco**

### **Tabelas Principais**
1. **country** - Países
2. **state** - Estados/Províncias
3. **city** - Cidades
4. **address** - Endereços
5. **profile** - Perfis de usuário
6. **company** - Empresas (com status: ATIVO, BLOQUEADO, TESTE, INATIVO, PENDENTE)
7. **user** - Usuários do sistema (com cargo/função)
8. **apps** - Aplicações do sistema
9. **user_responsible** - Usuários responsáveis das empresas
10. **app_empresa** - Relacionamento entre aplicações e empresas

### **Tabelas de Relacionamento**
1. **user_company** - Relacionamento many-to-many entre usuários e empresas
2. **app_empresa** - Relacionamento many-to-many entre aplicações e empresas

## 🔗 **Relacionamentos**

### **Hierarquia de Endereços**
```
country (1) ←→ (N) state (1) ←→ (N) city (1) ←→ (N) address
```

### **Usuários e Empresas**
```
user (N) ←→ (N) company (através de user_company)
user (1) ←→ (1) profile
user (1) ←→ (0..1) address
```

### **Empresas e Responsáveis**
```
company (1) ←→ (N) user_responsible
company (1) ←→ (1) address
```

### **Aplicações e Empresas**
```
company (N) ←→ (N) apps (através de app_empresa)
```

## 📊 **Dados de Exemplo Incluídos**

### **Empresas**
- Arte Visual Soft (empresa principal)
- Tech Solutions (empresa de exemplo)

### **Aplicações**
- Core System
- Gestão de Usuários
- Dashboard Empresarial
- Relatórios

### **Usuários Responsáveis**
- João Silva (Gerente de TI - Arte Visual Soft)
- Maria Santos (Diretora de Operações - Tech Solutions)
- Carlos Oliveira (Desenvolvedor Senior - Arte Visual Soft)

### **Relacionamentos App-Empresa**
- Core System + Gestão de Usuários + Dashboard + Relatórios → Arte Visual Soft (ATIVO)
- Core System → Tech Solutions (TESTE)
- Gestão de Usuários → Tech Solutions (INATIVO)

## ✅ **Status das Migrações**

- **Total de Migrações**: 20
- **Tabelas Criadas**: 10 principais + 2 de relacionamento
- **Dados de Exemplo**: Incluídos para todas as tabelas principais
- **Constraints**: Todas as chaves estrangeiras configuradas
- **Triggers**: Campos `updated_at` atualizados automaticamente
- **Documentação**: Comentários em todas as tabelas e colunas

## 🚀 **Como Executar**

### **Execução Automática (Recomendado)**
1. Certifique-se de que o Flyway está configurado no `application.properties`
2. Reinicie a aplicação
3. As migrações serão executadas automaticamente

### **Execução Manual**
```bash
# Conectar ao banco
psql -U seu_usuario -d seu_banco

# Executar migrações específicas (se necessário)
\i src/main/resources/db/migration/V12__create_table_company.sql
\i src/main/resources/db/migration/V13__insert_sample_companies.sql
# ... etc
```

## 🔍 **Verificação**

Após executar todas as migrações, você pode verificar se tudo foi criado corretamente:

```sql
-- Listar todas as tabelas
\dt

-- Verificar estrutura de uma tabela específica
\d company

-- Verificar dados inseridos
SELECT * FROM company;
SELECT * FROM apps;
SELECT * FROM user_responsible;
SELECT * FROM app_empresa;
```

## 📝 **Notas Importantes**

1. **Ordem das Migrações**: As migrações devem ser executadas na ordem numérica
2. **Dependências**: Algumas tabelas dependem de outras (ex: company depende de address)
3. **Dados de Exemplo**: Podem ser modificados ou removidos conforme necessário
4. **UUIDs**: Todas as chaves primárias usam UUID com geração automática
5. **Timestamps**: Campos `created_at` e `updated_at` são preenchidos automaticamente

## 🆘 **Solução de Problemas**

### **Erro: "relation does not exist"**
- Verifique se as migrações anteriores foram executadas
- Execute as migrações na ordem correta

### **Erro: "function gen_random_uuid() does not exist"**
- Execute: `CREATE EXTENSION IF NOT EXISTS "uuid-ossp";`

### **Erro: "language plpgsql does not exist"**
- Execute: `CREATE LANGUAGE plpgsql;`

## 📚 **Próximos Passos**

Com todas as migrações executadas, o banco de dados estará completamente configurado e pronto para uso. Você pode:

1. Testar a API de login que retorna todas as informações do usuário
2. Criar novos usuários, empresas e aplicações
3. Implementar funcionalidades adicionais
4. Personalizar os dados de exemplo conforme necessário 