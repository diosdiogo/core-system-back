-- Criação da tabela menu
CREATE TABLE menu (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_app UUID NOT NULL,
    name VARCHAR(200) NOT NULL,
    icon TEXT,
    url TEXT,
    versao VARCHAR(100) DEFAULT '1.0',
    ativo BOOLEAN DEFAULT true,
    status VARCHAR(100) DEFAULT 'Liberado',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentários para documentação
COMMENT ON TABLE menu IS 'Tabela de menus do sistema';
COMMENT ON COLUMN menu.id IS 'Identificador único do menu';
COMMENT ON COLUMN menu.id_app IS 'Referência para a aplicação (FK para apps.id)';
COMMENT ON COLUMN menu.name IS 'Nome do menu';
COMMENT ON COLUMN menu.icon IS 'Ícone do menu (base64, URL ou caminho)';
COMMENT ON COLUMN menu.url IS 'URL do menu';
COMMENT ON COLUMN menu.versao IS 'Versão do menu';
COMMENT ON COLUMN menu.ativo IS 'Status ativo/inativo do menu';
COMMENT ON COLUMN menu.status IS 'Status atual do menu';
COMMENT ON COLUMN menu.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN menu.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_menu_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_menu_updated_at 
    BEFORE UPDATE ON menu 
    FOR EACH ROW 
    EXECUTE FUNCTION update_menu_updated_at_column();

-- Adicionar constraint de chave estrangeira para id_app
ALTER TABLE menu 
ADD CONSTRAINT fk_menu_app 
FOREIGN KEY (id_app) REFERENCES apps(id) ON DELETE CASCADE; 