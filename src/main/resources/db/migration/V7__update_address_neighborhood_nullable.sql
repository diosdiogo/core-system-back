-- Alterar coluna neighborhood para aceitar valores nulos
-- Isso permite que endereços sejam cadastrados sem bairro
ALTER TABLE address
ALTER COLUMN neighborhood DROP NOT NULL;

-- Comentário explicativo
COMMENT ON COLUMN address.neighborhood IS 'Bairro do endereço (opcional) - pode ser nulo'; 