CREATE TABLE IF NOT EXISTS Users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username      TEXT UNIQUE NOT NULL,
    password      TEXT        NOT NULL,
    email         TEXT UNIQUE,
    phone         TEXT UNIQUE,
    full_name     TEXT        NOT NULL,
    birth_date    DATE        NOT NULL,
    role          TEXT        NOT NULL,
    refresh_token TEXT
);

CREATE INDEX ix_username ON Users (username);
CREATE INDEX ix_email ON Users (email);
CREATE INDEX ix_phone ON Users (phone);