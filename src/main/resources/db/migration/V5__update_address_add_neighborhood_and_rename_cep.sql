-- Adicionar coluna neighborhood depois de number
-- Obs: No PostgreSQL, a ordem das colunas não importa, mas se quiser organizar manualmente precisaria recriar a tabela.
ALTER TABLE address
ADD COLUMN neighborhood VARCHAR(100);

-- Renomear coluna cep para zipCode
ALTER TABLE address
RENAME COLUMN cep TO zipCode;

-- Ajustar tamanho/constraint de zipCode se necessário
ALTER TABLE address
ALTER COLUMN zipCode TYPE VARCHAR(100);
