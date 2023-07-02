package com.rova.transactionservice.dals;

import com.rova.transactionservice.enums.BalanceAction;
import com.rova.transactionservice.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "transactions")
public class Transaction extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private TransactionType type;

    @Column
    private BigDecimal balanceBefore;

    @Column
    private BigDecimal balanceAfter;

    @Column
    private BalanceAction action;

    @Column( nullable = false, updatable = false)
    private String accountNumber;

    @Column(nullable = false, updatable = false, length = 5)
    private String currency;

    @Column(nullable = false, updatable = false)
    private long currencyId;

    @Column(nullable = false, updatable = false)
    private long userId;

    @Column(updatable = false)
    private Long walletId;

    @PrePersist
    public void setBalanceAction() {
        this.action = getBalanceAction();
    }

    private BalanceAction getBalanceAction(){
        if(this.type == TransactionType.ACCOUNT_OPENING) return BalanceAction.CREDIT;
        return BalanceAction.DEBIT;
    }
}
