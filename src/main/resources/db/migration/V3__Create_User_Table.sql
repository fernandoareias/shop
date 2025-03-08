CREATE TABLE users (
    id_user BIGSERIAL PRIMARY KEY,
    des_email VARCHAR(150) UNIQUE NOT NULL,
    des_password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
    id_role BIGSERIAL PRIMARY KEY,
    des_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL REFERENCES users(id_user),
    role_id BIGINT NOT NULL REFERENCES roles(id_role),
    PRIMARY KEY (user_id, role_id)
);