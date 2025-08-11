CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE country (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL
);
