CREATE TABLE currencies (
    id BIGINT AUTO_INCREMENT NOT NULL,
    currency VARCHAR(30) NOT NULL,
    country VARCHAR(50) NOT NULL,
    country_code VARCHAR(5) NOT NULL,
    currency_code VARCHAR(5) NOT NULL,
    symbol varchar(10) NOT NULL,
    CONSTRAINT pk_currencies PRIMARY KEY (id),
    INDEX idx_country_code (country_code),
    INDEX idx_currency_code (currency_code)
);
