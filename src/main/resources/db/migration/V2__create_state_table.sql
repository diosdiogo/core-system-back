CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE state (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    country_id UUID NOT NULL,
    CONSTRAINT fk_state_country FOREIGN KEY (country_id) REFERENCES country(id)
);
