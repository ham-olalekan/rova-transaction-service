package com.rova.transactionservice.services.impl;

import com.rova.transactionservice.dals.Currency;
import com.rova.transactionservice.dals.Transaction;
import com.rova.transactionservice.dals.Wallet;
import com.rova.transactionservice.dto.CreateWalletDto;
import com.rova.transactionservice.dto.WalletDto;
import com.rova.transactionservice.enums.TransactionType;
import com.rova.transactionservice.exceptions.NotFoundException;
import com.rova.transactionservice.repository.WalletsRepository;
import com.rova.transactionservice.services.ICurrencyService;
import com.rova.transactionservice.services.ITransactionService;
import com.rova.transactionservice.services.IWalletService;
import com.rova.transactionservice.util.Generators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    private final WalletsRepository walletsRepository;

    private final ICurrencyService iCurrencyService;

    private final ITransactionService transactionService;

    @Override
    @Transactional
    public WalletDto createWallet(long userId, CreateWalletDto createWalletDto) throws NotFoundException {

        Currency currency = iCurrencyService
                .findByCountryCodeAndCurrencyCode(createWalletDto.getCountryCode(), createWalletDto.getCurrencyCode())
                .orElseThrow(() -> new NotFoundException("Unsupported country and currency code"));


        Wallet wallet = new Wallet();
        wallet.setCurrency(currency.getCurrencyCode());
        wallet.setCurrencyId(currency.getId());
        wallet.setUserId(userId);
        wallet.setAvailableBalance(createWalletDto.getAmount());
        wallet.setType(createWalletDto.getType());
        wallet.setAvailableBalance(createWalletDto.getAmount());
        wallet.setLedgerBalance(createWalletDto.getAmount());
        wallet.setFirstName(createWalletDto.getFirstName());
        wallet.setLastName(createWalletDto.getLastName());
        wallet.setAccountNumber(Generators.generateUniqueAccountNumbers());

        wallet = walletsRepository.save(wallet);

        WalletDto walletDto = WalletDto.fromModel(wallet);
        //create a transactions
        if (wallet.getAvailableBalance().compareTo(BigDecimal.ZERO) > 0) {
            createAccountCreationTransaction(createWalletDto.getAmount(), walletDto);
        }
        log.info("account creation of {} account for customer with id:[{}] successful", createWalletDto.getType(), userId);
        return WalletDto.fromModel(wallet);
    }

    private void createAccountCreationTransaction(BigDecimal amount, WalletDto walletDto) {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            log.debug("Terminating ");
            return;
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAccountNumber(walletDto.getAccountNumber());
        transaction.setCurrency(walletDto.getCurrency());
        transaction.setCurrencyId(walletDto.getCurrencyId());
        transaction.setUserId(walletDto.getUserId());
        transaction.setType(TransactionType.ACCOUNT_OPENING);
        transaction.setBalanceBefore(BigDecimal.ZERO);
        transaction.setBalanceAfter(walletDto.getAvailableBalance());
        transaction.setWalletId(walletDto.getId());
        transactionService.save(transaction);
    }

    @Override
    public WalletDto getWalletBalance(long userId, String reference) throws NotFoundException {
        Wallet wallet = walletsRepository.findByUserIdAndReference(userId, reference)
                .orElseThrow(() -> new NotFoundException("Account Not Found"));
        return WalletDto.fromModel(wallet);
    }
}
