CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE address (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    street VARCHAR(100) NOT NULL,
    number VARCHAR(100) NOT NULL,
    complement VARCHAR(100) NOT NULL,
    cep VARCHAR(100) NOT NULL,
    city_id UUID NOT NULL,
    CONSTRAINT fk_address_city FOREIGN KEY (city_id) REFERENCES city(id)
);
