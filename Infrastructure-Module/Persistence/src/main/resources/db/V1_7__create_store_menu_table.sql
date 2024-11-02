CREATE TABLE store_menu
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    name          VARCHAR(255) NOT NULL,
    price         DOUBLE       NOT NULL,
    store_id      VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_store_menu PRIMARY KEY (id)
);

ALTER TABLE store_menu
    ADD CONSTRAINT FK_STORE_MENU_ON_STOREID FOREIGN KEY (store_id) REFERENCES store (id);