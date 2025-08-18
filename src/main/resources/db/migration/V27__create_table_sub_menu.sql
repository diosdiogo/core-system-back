-- Criação da tabela sub_menu
CREATE TABLE sub_menu (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_menu UUID NOT NULL,
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
COMMENT ON TABLE sub_menu IS 'Tabela de submenus do sistema';
COMMENT ON COLUMN sub_menu.id IS 'Identificador único do submenu';
COMMENT ON COLUMN sub_menu.id_menu IS 'Referência para o menu pai (FK para menu.id)';
COMMENT ON COLUMN sub_menu.name IS 'Nome do submenu';
COMMENT ON COLUMN sub_menu.icon IS 'Ícone do submenu (base64, URL ou caminho)';
COMMENT ON COLUMN sub_menu.url IS 'URL do submenu';
COMMENT ON COLUMN sub_menu.versao IS 'Versão do submenu';
COMMENT ON COLUMN sub_menu.ativo IS 'Status ativo/inativo do submenu';
COMMENT ON COLUMN sub_menu.status IS 'Status atual do submenu';
COMMENT ON COLUMN sub_menu.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN sub_menu.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_sub_menu_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_sub_menu_updated_at 
    BEFORE UPDATE ON sub_menu 
    FOR EACH ROW 
    EXECUTE FUNCTION update_sub_menu_updated_at_column();

-- Chave estrangeira
ALTER TABLE sub_menu 
ADD CONSTRAINT fk_sub_menu_menu 
FOREIGN KEY (id_menu) REFERENCES menu(id) ON DELETE CASCADE;
