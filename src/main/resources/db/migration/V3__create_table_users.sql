
CREATE TABLE IF NOT EXISTS users (
    id bigint generated always as identity PRIMARY KEY,
    username VARCHAR(40) UNIQUE NOT NULL,
    password VARCHAR(80) NOT NULL,
    name VARCHAR(20),
    surname VARCHAR(20),
    parent_name VARCHAR(20),
    creation_date TIMESTAMP DEFAULT NOW(),
    last_edit_date TIMESTAMP
);

