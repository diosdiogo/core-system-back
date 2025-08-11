-- Criação da tabela user
CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    user_system VARCHAR(100) NOT NULL,
    password VARCHAR(200) NOT NULL,
    contato VARCHAR(200) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    address_id UUID,
    profile_id UUID NOT NULL,
    
    -- Constraints
    CONSTRAINT fk_user_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES profile(id),
    
    -- Índices para melhor performance
    CONSTRAINT uk_user_email UNIQUE (email),
    CONSTRAINT uk_user_system UNIQUE (user_system)
);

-- Comentários para documentação
COMMENT ON TABLE "user" IS 'Tabela de usuários do sistema';
COMMENT ON COLUMN "user".id IS 'Identificador único do usuário';
COMMENT ON COLUMN "user".name IS 'Nome completo do usuário';
COMMENT ON COLUMN "user".email IS 'E-mail único do usuário';
COMMENT ON COLUMN "user".user_system IS 'Nome de usuário para login no sistema';
COMMENT ON COLUMN "user".password IS 'Senha criptografada do usuário';
COMMENT ON COLUMN "user".contato IS 'Informações de contato (telefone, etc.)';
COMMENT ON COLUMN "user".active IS 'Status ativo/inativo do usuário';
COMMENT ON COLUMN "user".address_id IS 'ID do endereço (opcional)';
COMMENT ON COLUMN "user".profile_id IS 'ID do perfil do usuário'; 