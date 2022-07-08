CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,
                      login TEXT NOT NULL UNIQUE,
                      password TEXT NOT NULL,
                      created TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);