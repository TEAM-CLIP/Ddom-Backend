ALTER TABLE store_discount_policy
    ADD COLUMN discount_type VARCHAR(20) NOT NULL,
    ADD COLUMN discount_value VARCHAR(50) NOT NULL,
    ADD COLUMN is_default BIT(1) NOT NULL,
    ADD COLUMN visited_cnt INT NOT NULL,
    DROP COLUMN method,
    DROP COLUMN title;