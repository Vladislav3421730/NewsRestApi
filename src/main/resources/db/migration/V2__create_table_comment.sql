
CREATE TABLE IF NOT EXISTS comment (
    id BIGINT generated always as identity PRIMARY KEY,
    text VARCHAR(300),
    creation_date TIMESTAMP DEFAULT NOW(),
    last_edit_date TIMESTAMP,
    inserted_by_id BIGINT,
    news_id BIGINT,
    CONSTRAINT fk_comment_news FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE ON UPDATE CASCADE
);

