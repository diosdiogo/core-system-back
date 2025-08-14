-- Adicionar coluna cargo na tabela user
ALTER TABLE "user" ADD COLUMN cargo VARCHAR(100);

-- Comentário para documentação
COMMENT ON COLUMN "user".cargo IS 'Cargo/função do usuário na empresa';

-- Criar índice para melhor performance em consultas por cargo
CREATE INDEX idx_user_cargo ON "user"(cargo);

-- Atualizar registros existentes com cargo padrão se necessário
-- UPDATE "user" SET cargo = 'Colaborador' WHERE cargo IS NULL; 