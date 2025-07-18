
CREATE TABLE IF NOT EXISTS news (
    id bigint generated always as identity PRIMARY KEY,
    title VARCHAR(150),
    text VARCHAR(2000),
    creation_date TIMESTAMP DEFAULT NOW(),
    last_edit_date TIMESTAMP,
    inserted_by_id BIGINT,
    updated_by_id BIGINT
);

