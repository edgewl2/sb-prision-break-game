CREATE TABLE IF NOT EXISTS register (
    id SERIAL,
    prison VARCHAR(1000) NOT NULL,
    can_escape BOOLEAN DEFAULT FALSE,
    created TIMESTAMP,
    updated TIMESTAMP
);
