-- Adicionar coluna tipo na tabela company
ALTER TABLE "company" ADD COLUMN tipo VARCHAR(100) NOT NULL DEFAULT 'Matriz';

-- Comentário para documentação
COMMENT ON COLUMN "company".tipo IS 'Tipo da empresa, Matriz ou Filial';

-- Criar índice para melhor performance em consultas por tipo
CREATE INDEX idx_company_tipo ON "company"(tipo);

-- Atualizar registros existentes com tipo padrão se necessário
-- UPDATE "comapany" SET tipo = 'Matriz' WHERE tipo IS NULL; 