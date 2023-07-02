package com.rova.transactionservice.events.listener;

import com.rova.transactionservice.events.AccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountCreatedEventListener {


    @Async
    @EventListener
    public void handleAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent){
        log.info("::: Event for account created received:::");
        System.out.println(accountCreatedEvent);
    }
}
