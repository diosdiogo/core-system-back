CREATE TABLE profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(150) NOT NULL,
    status BOOLEAN NOT NULL,
    company_id UUID NOT NULL,
    CONSTRAINT fk_profile_company FOREIGN KEY (company_id) REFERENCES company(id)
);
