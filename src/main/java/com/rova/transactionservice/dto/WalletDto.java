package com.rova.transactionservice.dto;

import com.rova.transactionservice.dals.Wallet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletDto {

    private long id;
    private String accountNumber;
    private BigDecimal ledgerBalance;
    private BigDecimal availableBalance;
    private String currency;
    private long currencyId;
    private long userId;

    private String firstName;
    private String lastName;

    private String Reference;

    private String country;

    public static WalletDto fromModel(Wallet wallet) {
        WalletDto dto = new WalletDto();
        BeanUtils.copyProperties(wallet, dto);
        return dto;
    }
}
