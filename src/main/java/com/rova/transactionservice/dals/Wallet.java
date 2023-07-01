package com.rova.transactionservice.dals;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
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
    private long currency_id;

    @Column(nullable = false, updatable = false)
    private long user_id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;
}
