package com.rova.transactionservice.dals;

import com.rova.transactionservice.util.enums.BalanceAction;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity(name = "transactions")
public class Transaction extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal amount;

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
    private long currency_id;

    @Column(nullable = false, updatable = false)
    private long user_id;

    @Column(length = 64, nullable = false, updatable = false, unique = true)
    private String reference;

    @PrePersist
    public void appendReference() {
        if (!StringUtils.hasText(this.reference)) {
            this.reference = UUID.randomUUID().toString().replaceAll("-", "");
        }
    }
}
