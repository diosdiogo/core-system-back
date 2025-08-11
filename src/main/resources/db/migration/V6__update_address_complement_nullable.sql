-- Alterar coluna complement para aceitar valores nulos
-- Isso permite que endereços sejam cadastrados sem complemento
ALTER TABLE address
ALTER COLUMN complement DROP NOT NULL;

-- Comentário explicativo
COMMENT ON COLUMN address.complement IS 'Complemento do endereço (opcional) - pode ser nulo'; 