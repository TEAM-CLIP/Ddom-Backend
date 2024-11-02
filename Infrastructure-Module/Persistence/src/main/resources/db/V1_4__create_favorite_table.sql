CREATE TABLE favorite_store
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    visited_cnt   INT          NOT NULL,
    store_id      VARCHAR(255) NOT NULL,
    user_id       VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_favorite_store PRIMARY KEY (id)
);

ALTER TABLE favorite_store
    ADD CONSTRAINT FK_FAVORITE_STORE_ON_STOREID FOREIGN KEY (store_id) REFERENCES store (id);

ALTER TABLE favorite_store
    ADD CONSTRAINT FK_FAVORITE_STORE_ON_USERID FOREIGN KEY (user_id) REFERENCES user (id);