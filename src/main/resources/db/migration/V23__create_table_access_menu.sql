-- Criação da tabela access_menu
CREATE TABLE access_menu (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_app_company UUID NOT NULL,
    id_perfil UUID NOT NULL,
    "create" BOOLEAN DEFAULT false,
    "update" BOOLEAN DEFAULT false,
    "delete" BOOLEAN DEFAULT false,
    export BOOLEAN DEFAULT false,
    imprimir BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentários para documentação
COMMENT ON TABLE access_menu IS 'Tabela de controle de permissões de acesso aos menus por perfil';
COMMENT ON COLUMN access_menu.id IS 'Identificador único do acesso ao menu';
COMMENT ON COLUMN access_menu.id_app_company IS 'Referência para a aplicação da empresa (FK para app_company.id)';
COMMENT ON COLUMN access_menu.id_perfil IS 'Referência para o perfil (FK para profile.id)';
COMMENT ON COLUMN access_menu."create" IS 'Permissão para criar registros';
COMMENT ON COLUMN access_menu."update" IS 'Permissão para atualizar registros';
COMMENT ON COLUMN access_menu."delete" IS 'Permissão para excluir registros';
COMMENT ON COLUMN access_menu.export IS 'Permissão para exportar dados';
COMMENT ON COLUMN access_menu.imprimir IS 'Permissão para imprimir dados';
COMMENT ON COLUMN access_menu.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN access_menu.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_access_menu_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_access_menu_updated_at 
    BEFORE UPDATE ON access_menu 
    FOR EACH ROW 
    EXECUTE FUNCTION update_access_menu_updated_at_column();

-- Adicionar constraints de chave estrangeira
ALTER TABLE access_menu 
ADD CONSTRAINT fk_access_menu_app_company 
FOREIGN KEY (id_app_company) REFERENCES app_company(id) ON DELETE CASCADE;

ALTER TABLE access_menu 
ADD CONSTRAINT fk_access_menu_profile 
FOREIGN KEY (id_perfil) REFERENCES profile(id) ON DELETE CASCADE;

-- Adicionar constraint de unicidade para evitar duplicatas
ALTER TABLE access_menu 
ADD CONSTRAINT uk_access_menu_unique 
UNIQUE (id_app_company, id_perfil); 