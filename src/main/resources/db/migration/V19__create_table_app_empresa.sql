-- Criação da tabela app_empresa para relacionamento entre aplicações e empresas
CREATE TABLE app_company(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    app_id UUID NOT NULL,
    company_id UUID NOT NULL,
    ativo BOOLEAN DEFAULT true,
    status VARCHAR(50) DEFAULT 'ATIVO',
    validade DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT fk_app_empresa_app FOREIGN KEY (app_id) REFERENCES apps(id) ON DELETE CASCADE,
    CONSTRAINT fk_app_company_company FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE,
    
    -- Índice único para evitar duplicatas
    CONSTRAINT uk_app_company UNIQUE (app_id, company_id)
);

-- Comentários para documentação
COMMENT ON TABLE app_company IS 'Tabela de relacionamento entre aplicações e empresas';
COMMENT ON COLUMN app_company.id IS 'Identificador único do relacionamento';
COMMENT ON COLUMN app_company.app_id IS 'ID da aplicação';
COMMENT ON COLUMN app_company.company_id IS 'ID da empresa';
COMMENT ON COLUMN app_company.ativo IS 'Indica se o relacionamento está ativo';
COMMENT ON COLUMN app_company.status IS 'Status do relacionamento (ATIVO, INATIVO, BLOQUEADO, etc.)';
COMMENT ON COLUMN app_company.validade IS 'Data de validade do relacionamento';
COMMENT ON COLUMN app_company.created_at IS 'Data de criação do relacionamento';
COMMENT ON COLUMN app_company.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_app_company_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_app_company_updated_at 
    BEFORE UPDATE ON app_company 
    FOR EACH ROW 
    EXECUTE FUNCTION update_app_company_updated_at();

-- Criar índices para melhor performance
CREATE INDEX idx_app_company_app_id ON app_company(app_id);
CREATE INDEX idx_app_company_company_id ON app_company(company_id);
CREATE INDEX idx_app_company_ativo ON app_company(ativo);
CREATE INDEX idx_app_company_status ON app_company(status);
CREATE INDEX idx_app_company_validade ON app_company(validade); 