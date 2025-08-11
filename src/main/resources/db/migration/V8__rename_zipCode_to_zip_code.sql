-- Renomear coluna zipCode para zip_code para manter consistência com padrão snake_case
ALTER TABLE address
RENAME COLUMN zipCode TO zip_code;

-- Comentário explicativo
COMMENT ON COLUMN address.zip_code IS 'Código postal do endereço'; 