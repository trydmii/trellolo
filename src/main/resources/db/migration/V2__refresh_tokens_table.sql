CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE ,
    expiration TIMESTAMP NOT NULL,
    foreign key (user_id) references users(id)
);