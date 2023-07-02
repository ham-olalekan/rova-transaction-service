package com.rova.transactionservice.events;

import com.rova.transactionservice.dto.WalletDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class AccountCreatedEvent extends ApplicationEvent {

    private WalletDto walletDto;

    /**
     * handles all activities to be performed after
     * a new account has been created
     *
     * @param source
     * @param walletDto
     */
    public AccountCreatedEvent(Object source, WalletDto walletDto) {
        super(source);
        this.walletDto = walletDto;
    }
}
