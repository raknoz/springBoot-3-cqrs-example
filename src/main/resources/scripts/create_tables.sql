CREATE TABLE IF NOT EXISTS users
(
    id        serial PRIMARY KEY,
    firstname VARCHAR(50),
    lastname  VARCHAR(50),
    state     VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS product
(
    id          serial PRIMARY KEY,
    description VARCHAR(500),
    price       numeric(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS purchase_order
(
    id         serial PRIMARY KEY,
    user_id    integer references users (id),
    product_id integer references product (id),
    order_date date
);