CREATE TABLE users
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    username   varchar(50)  NOT NULL UNIQUE,
    first_name varchar(50)  NOT NULL,
    last_name  varchar(50)  NOT NULL,
    password   varchar(150) NOT NULL,
    user_status varchar(50)  NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE files
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name      varchar(50) NOT NULL UNIQUE,
    author_id bigint      NOT NULL,
    FOREIGN KEY (author_id) REFERENCES files (id) ON DELETE CASCADE
);

CREATE TABLE user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE RESTRICT
) ;

INSERT INTO users (username, first_name, last_name, password)
VALUES ('trydmi', 'Dmytro', 'Pushkar', '$2a$04$Ns8NHUz4iBorRF.6.wX64.g4CldywEXIW11gbR.0wBidIgRUjE1Be');

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_MODERATOR'),
       ('ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 3);