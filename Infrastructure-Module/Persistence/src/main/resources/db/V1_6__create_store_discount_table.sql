CREATE TABLE store_discount_policy
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    title         VARCHAR(255) NOT NULL,
    method        VARCHAR(20)  NOT NULL,
    store_id      VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_store_discount_policy PRIMARY KEY (id)
);

ALTER TABLE store_discount_policy
    ADD CONSTRAINT FK_STORE_DISCOUNT_POLICY_ON_STOREID FOREIGN KEY (store_id) REFERENCES store (id);