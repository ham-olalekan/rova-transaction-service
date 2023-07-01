package com.rova.transactionservice.dto;

import com.rova.transactionservice.dals.Transaction;
import com.rova.transactionservice.util.enums.BalanceAction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
@Getter
@Setter
public class TransactionDto {
    private String reference;
    private BigDecimal amount;

    private BalanceAction action;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    private String accountNumber;

    private String currency;

    public static TransactionDto fromModel(Transaction transaction){
        TransactionDto dto = new TransactionDto();
        BeanUtils.copyProperties(transaction, dto);
        return dto;
    }
}
