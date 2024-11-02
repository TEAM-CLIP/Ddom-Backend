CREATE TABLE zone
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    name          VARCHAR(100) NOT NULL,
    boundary      VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_zone PRIMARY KEY (id)
);


CREATE TABLE category
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    type          VARCHAR(20)  NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE store
(
    id            VARCHAR(255) NOT NULL,
    created_at    datetime     NULL,
    updated_at    datetime     NULL,
    name          VARCHAR(255) NOT NULL,
    img_url       VARCHAR(255) NULL,
    introduction  VARCHAR(500) NULL,
    is_registered BIT(1)       NOT NULL,
    longitude     DOUBLE       NOT NULL,
    latitude      DOUBLE       NOT NULL,
    category_id   VARCHAR(255) NOT NULL,
    zone_id       VARCHAR(255) NOT NULL,
    entity_status VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_store PRIMARY KEY (id)
);

ALTER TABLE store
    ADD CONSTRAINT FK_STORE_ON_CATEGORYID FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE store
    ADD CONSTRAINT FK_STORE_ON_ZONEID FOREIGN KEY (zone_id) REFERENCES zone (id);