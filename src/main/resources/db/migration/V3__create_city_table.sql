CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE city (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    state_id UUID NOT NULL,
    CONSTRAINT fk_city_state FOREIGN KEY (state_id) REFERENCES state(id)
);
