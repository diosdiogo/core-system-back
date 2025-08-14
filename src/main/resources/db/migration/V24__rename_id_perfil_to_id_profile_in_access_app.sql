-- Renomear coluna id_perfil para id_profile na tabela access_app
ALTER TABLE access_app RENAME COLUMN id_perfil TO id_profile;

-- Atualizar comentário da coluna
COMMENT ON COLUMN access_app.id_profile IS 'Referência para o perfil (FK para profile.id)';

-- Remover constraint de chave estrangeira antiga
ALTER TABLE access_app DROP CONSTRAINT IF EXISTS fk_access_app_profile;

-- Adicionar nova constraint de chave estrangeira com o novo nome
ALTER TABLE access_app 
ADD CONSTRAINT fk_access_app_profile 
FOREIGN KEY (id_profile) REFERENCES profile(id) ON DELETE CASCADE; 