CREATE TABLE categories
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    state         VARCHAR(255) NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE categories_featured_products
(
    categories_id        BIGINT NOT NULL,
    featured_products_id BIGINT NOT NULL
);

CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    datetime NULL,
    updated_at    datetime NULL,
    state         VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    price DOUBLE NULL,
    `description` VARCHAR(255) NULL,
    category_id   BIGINT NOT NULL,
    image_url     VARCHAR(255) NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE ratings
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    state      VARCHAR(255) NULL,
    rate DOUBLE NULL,
    review     VARCHAR(255) NULL,
    product_id BIGINT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (id)
);

ALTER TABLE categories_featured_products
    ADD CONSTRAINT uc_categories_featured_products_featuredproducts UNIQUE (featured_products_id);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_name UNIQUE (name);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE ratings
    ADD CONSTRAINT FK_RATINGS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE categories_featured_products
    ADD CONSTRAINT fk_catfeapro_on_category FOREIGN KEY (categories_id) REFERENCES categories (id);

ALTER TABLE categories_featured_products
    ADD CONSTRAINT fk_catfeapro_on_product FOREIGN KEY (featured_products_id) REFERENCES products (id);