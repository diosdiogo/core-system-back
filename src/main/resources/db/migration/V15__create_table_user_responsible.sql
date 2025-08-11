-- Criação da tabela user_responsible
CREATE TABLE user_responsible (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    company_id UUID NOT NULL,
    cargo VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT fk_user_responsible_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_responsible_company FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE,
    
    -- Índice único para evitar duplicatas
    CONSTRAINT uk_user_responsible_user_company UNIQUE (user_id, company_id)
);

-- Comentários para documentação
COMMENT ON TABLE user_responsible IS 'Tabela de usuários responsáveis das empresas';
COMMENT ON COLUMN user_responsible.id IS 'Identificador único do usuário responsável';
COMMENT ON COLUMN user_responsible.user_id IS 'ID do usuário responsável';
COMMENT ON COLUMN user_responsible.company_id IS 'ID da empresa à qual o usuário é responsável';
COMMENT ON COLUMN user_responsible.cargo IS 'Cargo/função do usuário responsável';
COMMENT ON COLUMN user_responsible.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN user_responsible.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_user_responsible_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_user_responsible_updated_at 
    BEFORE UPDATE ON user_responsible 
    FOR EACH ROW 
    EXECUTE FUNCTION update_user_responsible_updated_at_column(); 