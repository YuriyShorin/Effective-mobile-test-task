CREATE TABLE IF NOT EXISTS Users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email         TEXT UNIQUE NOT NULL,
    password      TEXT        NOT NULL,
    phone         TEXT        NOT NULL,
    full_name     TEXT        NOT NULL,
    role          TEXT        NOT NULL,
    refresh_token TEXT
);