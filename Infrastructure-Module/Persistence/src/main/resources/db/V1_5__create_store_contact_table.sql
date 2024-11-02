CREATE TABLE store_contact
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    store_id      VARCHAR(255) NULL,
    contact_type  VARCHAR(20)  NOT NULL,
    value         VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_store_contact PRIMARY KEY (id)
);

ALTER TABLE store_contact
    ADD CONSTRAINT FK_STORE_CONTACT_ON_STOREID FOREIGN KEY (store_id) REFERENCES store (id);