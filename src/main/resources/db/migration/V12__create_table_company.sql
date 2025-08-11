-- Criação da tabela company
CREATE TABLE company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name_fant VARCHAR(150) NOT NULL,
    razao_social VARCHAR(150) NOT NULL,
    ie VARCHAR(50) NOT NULL,
    im VARCHAR(50) NOT NULL,
    cnpj VARCHAR(150) NOT NULL,
    matriz VARCHAR(150) NOT NULL,
    address_id UUID NOT NULL,
    social_media VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT fk_company_address FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE RESTRICT,
    
    -- Índices para melhor performance
    CONSTRAINT uk_company_cnpj UNIQUE (cnpj),
    CONSTRAINT uk_company_ie UNIQUE (ie),
    CONSTRAINT uk_company_im UNIQUE (im)
);

-- Comentários para documentação
COMMENT ON TABLE company IS 'Tabela de empresas do sistema';
COMMENT ON COLUMN company.id IS 'Identificador único da empresa';
COMMENT ON COLUMN company.name_fant IS 'Nome fantasia da empresa';
COMMENT ON COLUMN company.razao_social IS 'Razão social da empresa';
COMMENT ON COLUMN company.ie IS 'Inscrição Estadual da empresa';
COMMENT ON COLUMN company.im IS 'Inscrição Municipal da empresa';
COMMENT ON COLUMN company.cnpj IS 'CNPJ da empresa';
COMMENT ON COLUMN company.matriz IS 'Tipo de matriz (Matriz/Filial)';
COMMENT ON COLUMN company.address_id IS 'ID do endereço da empresa';
COMMENT ON COLUMN company.social_media IS 'Redes sociais da empresa';
COMMENT ON COLUMN company.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN company.updated_at IS 'Data da última atualização';

-- Trigger para atualizar o campo updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_company_updated_at 
    BEFORE UPDATE ON company 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column(); 