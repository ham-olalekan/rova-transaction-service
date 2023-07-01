package com.rova.transactionservice.dto;

import com.rova.transactionservice.dals.Wallet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletDto {
    private String accountNumber;
    private BigDecimal ledgerBalance;
    private BigDecimal availableBalance;
    private String currency;
    private long currency_id;
    private long user_id;

    private String firstName;
    private String lastName;

    public static WalletDto fromModel(Wallet wallet) {
        WalletDto dto = new WalletDto();
        BeanUtils.copyProperties(wallet, dto);
        return dto;
    }
}
