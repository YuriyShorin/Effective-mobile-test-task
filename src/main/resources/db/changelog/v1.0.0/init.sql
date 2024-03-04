CREATE TABLE IF NOT EXISTS Users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username      TEXT UNIQUE NOT NULL,
    password      TEXT        NOT NULL,
    full_name     TEXT        NOT NULL,
    birth_date    DATE        NOT NULL,
    role          TEXT        NOT NULL,
    refresh_token TEXT
);

CREATE INDEX ix_username ON Users (username);

CREATE TABLE IF NOT EXISTS Phones
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID,
    phone   TEXT NOT NULL UNIQUE,

    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS Emails
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID,
    email   TEXT NOT NULL UNIQUE,

    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS Accounts
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID,
    initial_deposit DECIMAL NOT NULL,
    balance         DECIMAL NOT NULL,

    CONSTRAINT user_fk FOREIGN KEY (user_id)
        REFERENCES Users (id)
);