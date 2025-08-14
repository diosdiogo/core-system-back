-- Criação da tabela access_app
CREATE TABLE access_app (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_perfil UUID NOT NULL,
    id_app_company UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentários para documentação
COMMENT ON TABLE access_app IS 'Tabela de controle de acesso às aplicações por perfil';
COMMENT ON COLUMN access_app.id IS 'Identificador único do acesso';
COMMENT ON COLUMN access_app.id_perfil IS 'Referência para o perfil (FK para profile.id)';
COMMENT ON COLUMN access_app.id_app_company IS 'Referência para a aplicação da empresa (FK para app_company.id)';
COMMENT ON COLUMN access_app.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN access_app.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_access_app_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_access_app_updated_at 
    BEFORE UPDATE ON access_app 
    FOR EACH ROW 
    EXECUTE FUNCTION update_access_app_updated_at_column();

-- Adicionar constraints de chave estrangeira
ALTER TABLE access_app 
ADD CONSTRAINT fk_access_app_profile 
FOREIGN KEY (id_perfil) REFERENCES profile(id) ON DELETE CASCADE;

ALTER TABLE access_app 
ADD CONSTRAINT fk_access_app_app_company 
FOREIGN KEY (id_app_company) REFERENCES app_company(id) ON DELETE CASCADE;

-- Adicionar constraint de unicidade para evitar duplicatas
ALTER TABLE access_app 
ADD CONSTRAINT uk_access_app_unique 
UNIQUE (id_perfil, id_app_company); 