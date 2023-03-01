CREATE TABLE IF NOT EXISTS balance
(
    uuid    VARCHAR(36) NOT NULL,
    economy VARCHAR(50) NOT NULL,
    amount  NUMERIC     NOT NULL,
    PRIMARY KEY (uuid, economy)
);