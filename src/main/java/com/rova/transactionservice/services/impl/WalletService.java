package com.rova.transactionservice.services.impl;

import com.rova.transactionservice.dals.Currency;
import com.rova.transactionservice.dals.Transaction;
import com.rova.transactionservice.dals.Wallet;
import com.rova.transactionservice.dto.CreateWalletDto;
import com.rova.transactionservice.dto.WalletDto;
import com.rova.transactionservice.enums.IdempotentAction;
import com.rova.transactionservice.enums.TransactionType;
import com.rova.transactionservice.enums.WalletType;
import com.rova.transactionservice.exceptions.CommonsException;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.InsufficientFundsException;
import com.rova.transactionservice.exceptions.NotFoundException;
import com.rova.transactionservice.repository.WalletsRepository;
import com.rova.transactionservice.services.*;
import com.rova.transactionservice.util.Constants;
import com.rova.transactionservice.util.Generators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    private final WalletsRepository walletsRepository;

    private final ICurrencyService iCurrencyService;

    private final ITransactionService transactionService;

    private final IdempotencyService idempotencyService;

    private final ILockService lockService;

    final int DEFAULT_EXPIRATION = 600;

    @Override
    @Transactional
    public WalletDto createWallet(long userId, CreateWalletDto createWalletDto) throws NotFoundException, DuplicateRequestException {
        String key = createWalletDto.getHash(userId, IdempotentAction.CREATE_ACCOUNT);
        String locked = idempotencyService.get(key);
        if (StringUtils.hasText(locked)) throw new DuplicateRequestException("Duplicate request for account creation");

        Optional<Wallet> existingWalletType = walletsRepository.findByUserIdAndTypeAndCurrencyCode(userId, createWalletDto.getType(), createWalletDto.getCurrencyCode());
        if (existingWalletType.isPresent())
            throw new DuplicateRequestException("User already has wallet of type " + createWalletDto.getType());

        idempotencyService.lock(key);
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
        idempotencyService.release(key);
        return WalletDto.fromModel(wallet);
    }

    private void createAccountCreationTransaction(BigDecimal amount, WalletDto walletDto) {
        if (amount.compareTo(BigDecimal.ZERO) < 1) {
            log.debug("Terminating createAccountCreationTransaction");
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

    @Override
    public WalletDto creditWallet(String transactionReference, String walletReference, BigDecimal amount) throws NotFoundException, CommonsException {
        final String lockKey = String.format("%s-%s-%s", Constants.LockPrefix.ACCOUNT_DEBIT, transactionReference, walletReference);
        boolean lockAcquired = lockService.acquireLock(lockKey, DEFAULT_EXPIRATION);

        try {
            if (!lockAcquired) {
                log.debug("creditWallet failed due to failed lock acquisition");
                throw new CommonsException("Operation already in progress", HttpStatus.LOCKED);
            }

            Optional<Wallet> walletOpt = walletsRepository.findByReference(walletReference);

            if (!walletOpt.isPresent()) {
                throw new NotFoundException("Account not found");
            }

            Wallet wallet = walletOpt.get();

            BigDecimal newBalance = wallet.getAvailableBalance().add(amount);
            wallet.setAvailableBalance(newBalance);
            walletsRepository.save(wallet);
            return WalletDto.fromModel(wallet);
        } finally {
            lockService.releaseLock(lockKey);
        }
    }

    @Override
    public WalletDto debitWallet(String transactionReference, String walletReference, BigDecimal amount)
            throws NotFoundException, InsufficientFundsException, CommonsException {

        final String lockKey = String.format("%s-%s-%s", Constants.LockPrefix.ACCOUNT_DEBIT, transactionReference, walletReference);
        boolean lockAcquired = lockService.acquireLock(lockKey, DEFAULT_EXPIRATION);

        try {
            if (!lockAcquired) {
                throw new CommonsException("Operation already in progress", HttpStatus.LOCKED);
            }

            Optional<Wallet> walletOpt = walletsRepository.findByReference(walletReference);

            if (!walletOpt.isPresent()) {
                throw new NotFoundException("Account not found");
            }

            Wallet wallet = walletOpt.get();
            if (wallet.getAvailableBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Wallet balance is insufficient for this debit request");
            }

            BigDecimal newBalance = wallet.getAvailableBalance().subtract(amount);
            wallet.setAvailableBalance(newBalance);
            walletsRepository.save(wallet);
            return WalletDto.fromModel(wallet);
        } finally {
            lockService.releaseLock(lockKey);
        }
    }

    @Override
    public Optional<Wallet> getWallet(long userId, WalletType type, String currencyCode) {
        return walletsRepository.findByUserIdAndTypeAndCurrencyCode(userId, type, currencyCode);
    }
}
