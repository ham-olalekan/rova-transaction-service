CREATE TABLE wallets (
    id BIGINT AUTO_INCREMENT NOT NULL,
    account_number VARCHAR(10) NOT NULL,
    reference VARCHAR(64) NOT NULL UNIQUE,
    ledger_balance DECIMAL NOT NULL DEFAULT 0,
    available_balance DECIMAL NOT NULL DEFAULT 0,
    type VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    currency_id BIGINT NOT NULL,
    currency VARCHAR(5) NOT NULL,
    user_id BIGINT NOT NULL,

    INDEX idx_wallet_user_id (user_id),
    INDEX idx_wallet_account_number (account_number),
    CONSTRAINT pk_WALLETS_ID PRIMARY KEY (id),
    CONSTRAINT FK_WALLETS_ON_CURRENCIES FOREIGN KEY (currency_id) REFERENCES currencies(id)
);
