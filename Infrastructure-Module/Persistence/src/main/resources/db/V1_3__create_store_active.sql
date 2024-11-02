CREATE TABLE store_active_time
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    day_of_week   VARCHAR(20)  NOT NULL,
    start_at      time         NOT NULL,
    end_at        time         NOT NULL,
    is_active     BIT(1)       NOT NULL,
    store_id      VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_store_active_time PRIMARY KEY (id)
);

ALTER TABLE store_active_time
    ADD CONSTRAINT FK_STORE_ACTIVE_TIME_ON_STOREID FOREIGN KEY (store_id) REFERENCES store (id);