CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT NOT NULL,
    account_number VARCHAR(10) NOT NULL,
    reference VARCHAR(64) NOT NULL UNIQUE,
    balance_before DECIMAL NOT NULL DEFAULT 0,
    balance_after DECIMAL NOT NULL DEFAULT 0,
    action VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    currency_id BIGINT NOT NULL,
    currency VARCHAR(5) NOT NULL,
    user_id BIGINT NOT NULL,
    wallet_id BIGINT NOT NULL,

   INDEX idx_trx_reference (reference),
   INDEX idx_trx_account_number (account_number),
   INDEX idx_trx_wallet_id (wallet_id),
   CONSTRAINT pk_TRANSACTION_ID PRIMARY KEY (id),
   CONSTRAINT FK_TRANSACTION_ON_CURRENCIES FOREIGN KEY (currency_id) REFERENCES currencies(id)
);