CREATE TABLE IF NOT EXISTS user_role
(
    user_id BIGINT      NOT NULL,
    role_set   VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
