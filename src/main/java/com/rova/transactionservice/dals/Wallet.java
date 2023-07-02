package com.rova.transactionservice.dals;

import com.rova.transactionservice.enums.WalletType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "wallets")
public class Wallet extends BaseEntity{

    @Column( nullable = false, updatable = false)
    private String accountNumber;

    @Column
    private BigDecimal ledgerBalance;

    @Column
    private BigDecimal availableBalance;

    @Column(nullable = false, updatable = false, length = 5)
    private String currency;

    @Column(nullable = false, updatable = false)
    private long currencyId;

    @Column(nullable = false, updatable = false)
    private long userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 10)
    private WalletType type;
}
