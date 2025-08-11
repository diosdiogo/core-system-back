# Scripts SQL para Tabela Company

Este diretório contém scripts SQL para criar e configurar a tabela `company` no banco de dados PostgreSQL.

## Arquivos Disponíveis

### 1. Migrações Flyway (Recomendado)
- **`V12__create_table_company.sql`** - Cria a tabela company
- **`V13__insert_sample_companies.sql`** - Insere dados de exemplo

### 2. Script Standalone
- **`create_company_table.sql`** - Script completo que pode ser executado diretamente

## Como Usar

### Opção 1: Migrações Flyway (Recomendado)
Se você estiver usando Flyway para gerenciar o banco de dados:

1. Os scripts `V12__` e `V13__` serão executados automaticamente quando a aplicação iniciar
2. Certifique-se de que o Flyway está configurado corretamente
3. Reinicie a aplicação para executar as migrações

### Opção 2: Script Manual
Para executar o script manualmente:

1. Conecte-se ao seu banco PostgreSQL
2. Execute o script `create_company_table.sql`:
   ```bash
   psql -U seu_usuario -d seu_banco -f create_company_table.sql
   ```

## Estrutura da Tabela

A tabela `company` inclui os seguintes campos:

| Campo | Tipo | Tamanho | Nullable | Descrição |
|-------|------|---------|----------|-----------|
| `id` | UUID | - | NOT NULL | Chave primária |
| `name_fant` | VARCHAR | 150 | NOT NULL | Nome fantasia |
| `razao_social` | VARCHAR | 150 | NOT NULL | Razão social |
| `ie` | VARCHAR | 50 | NOT NULL | Inscrição Estadual |
| `im` | VARCHAR | 50 | NOT NULL | Inscrição Municipal |
| `cnpj` | VARCHAR | 150 | NOT NULL | CNPJ |
| `matriz` | VARCHAR | 150 | NOT NULL | Tipo (Matriz/Filial) |
| `address_id` | UUID | - | NOT NULL | Referência ao endereço |
| `social_media` | VARCHAR | 150 | NOT NULL | Redes sociais |
| `created_at` | TIMESTAMP | - | NOT NULL | Data de criação |
| `updated_at` | TIMESTAMP | - | NOT NULL | Data de atualização |

## Constraints

- **Chave Primária**: `id` (UUID auto-gerado)
- **Chave Estrangeira**: `address_id` → `address(id)`
- **Únicos**: `cnpj`, `ie`, `im`
- **Trigger**: Atualiza automaticamente `updated_at` em modificações

## Dados de Exemplo

O script `V13__insert_sample_companies.sql` insere duas empresas de exemplo:
1. **Arte Visual Soft** - Empresa principal do sistema
2. **Tech Solutions** - Empresa de exemplo para testes

## Verificação

Após executar os scripts, você pode verificar se tudo foi criado corretamente:

```sql
-- Verificar se a tabela existe
SELECT table_name FROM information_schema.tables WHERE table_name = 'company';

-- Verificar estrutura da tabela
\d company

-- Verificar dados inseridos
SELECT * FROM company;
```

## Dependências

A tabela `company` depende das seguintes tabelas:
- `address` - Para o endereço da empresa
- `country`, `state`, `city` - Para a hierarquia de endereços

Certifique-se de que essas tabelas existem antes de executar os scripts.

## Problemas Comuns

### Erro: "relation 'address' does not exist"
- Execute primeiro as migrações V1 a V4 para criar as tabelas de endereço

### Erro: "function gen_random_uuid() does not exist"
- Execute: `CREATE EXTENSION IF NOT EXISTS "uuid-ossp";`

### Erro: "language 'plpgsql' does not exist"
- Execute: `CREATE LANGUAGE plpgsql;`

## Suporte

Se encontrar problemas, verifique:
1. Se todas as dependências estão criadas
2. Se você tem permissões suficientes no banco
3. Se a versão do PostgreSQL é compatível (recomendado 12+) 