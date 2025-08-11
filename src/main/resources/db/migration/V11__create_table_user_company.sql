-- Criação da tabela user_company para relacionamento many-to-many
CREATE TABLE user_company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    company_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT fk_user_company_user FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_company_company FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE,
    
    -- Índice único para evitar duplicatas
    CONSTRAINT uk_user_company UNIQUE (user_id, company_id)
);

-- Comentários para documentação
COMMENT ON TABLE user_company IS 'Tabela de relacionamento entre usuários e empresas';
COMMENT ON COLUMN user_company.id IS 'Identificador único do relacionamento';
COMMENT ON COLUMN user_company.user_id IS 'ID do usuário';
COMMENT ON COLUMN user_company.company_id IS 'ID da empresa';
COMMENT ON COLUMN user_company.created_at IS 'Data de criação do relacionamento'; 