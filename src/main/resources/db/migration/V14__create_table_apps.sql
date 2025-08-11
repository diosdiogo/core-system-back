-- Criação da tabela apps
CREATE TABLE apps (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(200) NOT NULL,
    url TEXT NOT NULL,
    icon TEXT,
    versao VARCHAR(100),
    ativo BOOLEAN DEFAULT true,
    status VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentários para documentação
COMMENT ON TABLE apps IS 'Tabela de aplicações do sistema';
COMMENT ON COLUMN apps.id IS 'Identificador único da aplicação';
COMMENT ON COLUMN apps.nome IS 'Nome da aplicação';
COMMENT ON COLUMN apps.url IS 'URL da aplicação';
COMMENT ON COLUMN apps.icon IS 'Ícone da aplicação (base64, URL ou caminho)';
COMMENT ON COLUMN apps.versao IS 'Versão da aplicação';
COMMENT ON COLUMN apps.ativo IS 'Status ativo/inativo da aplicação';
COMMENT ON COLUMN apps.status IS 'Status atual da aplicação';
COMMENT ON COLUMN apps.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN apps.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_apps_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_apps_updated_at 
    BEFORE UPDATE ON apps 
    FOR EACH ROW 
    EXECUTE FUNCTION update_apps_updated_at_column(); 