-- Renomear coluna id_perfil para id_profile na tabela access_menu
ALTER TABLE access_menu RENAME COLUMN id_perfil TO id_profile;

-- Atualizar comentário da coluna
COMMENT ON COLUMN access_menu.id_profile IS 'Referência para o perfil (FK para profile.id)';

-- Remover constraint de chave estrangeira antiga
ALTER TABLE access_menu DROP CONSTRAINT IF EXISTS fk_access_menu_profile;

-- Adicionar nova constraint de chave estrangeira com o novo nome
ALTER TABLE access_menu 
ADD CONSTRAINT fk_access_menu_profile 
FOREIGN KEY (id_profile) REFERENCES profile(id) ON DELETE CASCADE; 