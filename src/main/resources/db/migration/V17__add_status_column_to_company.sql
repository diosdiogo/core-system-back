-- Adicionar coluna status na tabela company
ALTER TABLE company ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'ATIVO';

-- Comentário para documentação
COMMENT ON COLUMN company.status IS 'Status da empresa: ATIVO, BLOQUEADO, TESTE, etc.';

-- Criar índice para melhor performance em consultas por status
CREATE INDEX idx_company_status ON company(status);

-- Atualizar registros existentes para ter o status padrão
UPDATE company SET status = 'ATIVO' WHERE status IS NULL;

-- Adicionar constraint para validar valores válidos de status
ALTER TABLE company ADD CONSTRAINT chk_company_status 
    CHECK (status IN ('ATIVO', 'BLOQUEADO', 'TESTE', 'INATIVO', 'PENDENTE'));

-- Comentário sobre a constraint
COMMENT ON CONSTRAINT chk_company_status ON company IS 'Validação dos valores permitidos para o status da empresa'; 