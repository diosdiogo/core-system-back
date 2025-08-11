-- Inserção de empresas de exemplo para testes
-- Nota: Este script assume que já existem endereços válidos na tabela address
-- Você pode modificar os address_id conforme necessário

-- Empresa de exemplo 1
INSERT INTO company (
    name_fant,
    razao_social,
    ie,
    im,
    cnpj,
    matriz,
    address_id,
    social_media
) VALUES (
    'Arte Visual Soft',
    'Arte Visual Software Ltda',
    '123456789',
    '987654321',
    '12.345.678/0001-90',
    'Matriz',
    (SELECT id FROM address LIMIT 1), -- Assumindo que existe pelo menos um endereço
    'Instagram: @artevizualsoft, LinkedIn: artevisualsoft'
);

-- Empresa de exemplo 2
INSERT INTO company (
    name_fant,
    razao_social,
    ie,
    im,
    cnpj,
    matriz,
    address_id,
    social_media
) VALUES (
    'Tech Solutions',
    'Tech Solutions Consultoria Ltda',
    '987654321',
    '123456789',
    '98.765.432/0001-10',
    'Matriz',
    (SELECT id FROM address LIMIT 1), -- Assumindo que existe pelo menos um endereço
    'Facebook: @techsolutions, Twitter: @techsol'
);

-- Comentário sobre o script
COMMENT ON TABLE company IS 'Dados de exemplo inseridos para facilitar testes do sistema'; 