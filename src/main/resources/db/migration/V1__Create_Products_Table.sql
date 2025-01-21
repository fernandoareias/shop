CREATE TABLE PRODUCTS (
    id_product BIGSERIAL PRIMARY KEY, -- ID com geração automática
    des_name VARCHAR(255) NOT NULL,  -- Nome do produto
    des_description TEXT,            -- Descrição do produto (opcional)
    vlw_price NUMERIC(15, 2) NOT NULL -- Preço com 15 dígitos no total e 2 casas decimais
);
