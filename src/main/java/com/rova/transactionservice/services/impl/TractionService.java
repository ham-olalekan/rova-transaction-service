package com.rova.transactionservice.services.impl;

import com.rova.transactionservice.dals.Currency;
import com.rova.transactionservice.dals.Transaction;
import com.rova.transactionservice.dals.Wallet;
import com.rova.transactionservice.dto.CreateTransactionDto;
import com.rova.transactionservice.dto.PageDto;
import com.rova.transactionservice.dto.TransactionDto;
import com.rova.transactionservice.enums.IdempotentAction;
import com.rova.transactionservice.enums.WalletType;
import com.rova.transactionservice.exceptions.CommonsException;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.NotFoundException;
import com.rova.transactionservice.repository.TransactionsRepository;
import com.rova.transactionservice.services.ICurrencyService;
import com.rova.transactionservice.services.ITransactionService;
import com.rova.transactionservice.services.IWalletService;
import com.rova.transactionservice.services.IdempotencyService;
import com.rova.transactionservice.util.IAppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TractionService implements ITransactionService {

    private final TransactionsRepository repository;

    private final ICurrencyService iCurrencyService;

    private final IdempotencyService idempotencyService;

    private final ApplicationEventPublisher publisher;

    @Override
    public TransactionDto createTransaction(long userId, CreateTransactionDto createTransactionDto) throws NotFoundException {
        Currency currency = iCurrencyService
                .findByCountryCodeAndCurrencyCode(createTransactionDto.getCountryCode(), createTransactionDto.getCurrencyCode())
                .orElseThrow(() -> new NotFoundException("Unsupported country and currency code"));

        Transaction transaction = new Transaction();
        transaction.setAmount(createTransactionDto.getAmount());
        transaction.setCurrency(createTransactionDto.getCurrencyCode());
        transaction.setCurrencyId(currency.getId());
        transaction.setAmount(createTransactionDto.getAmount());
        transaction.setUserId(userId);
        transaction.setType(createTransactionDto.getType());
        transaction = repository.save(transaction);
        return TransactionDto.fromModel(transaction);
    }

    @Override
    public TransactionDto save(Transaction transaction) {
        return TransactionDto.fromModel(repository.save(transaction));
    }

    @Override
    public PageDto getUserAccountTransaction(long userId, String accountReference, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Transaction> transactions = repository.findByUserIdAndAccountReference(userId, accountReference, pageRequest);
        return PageDto.build(transactions, TransactionDto::fromModel);
    }

//    @Override
//    @Transactional
//    public TransactionDto createTransaction(CreateTransactionDto createTransactionDto) throws NotFoundException, CommonsException, DuplicateRequestException {
//        if (Objects.isNull(createTransactionDto.getUserId()) || Objects.isNull(IAppendableReferenceUtils.getReferenceFrom(createTransactionDto.getUserId())))
//            throw new CommonsException("Invalid userId", HttpStatus.BAD_REQUEST);
//
//        long userId = IAppendableReferenceUtils.getIdFrom(createTransactionDto.getUserId());
//        String key = createTransactionDto.getHash(userId, IdempotentAction.CREATE_TRANSACTION);
//        String locked = idempotencyService.get(key);
//        if (StringUtils.hasText(locked))
//            throw new DuplicateRequestException("Duplicate request for transaction creation");
//
//      Optional<Wallet> walletOptional = walletService.getWallet(userId, WalletType.CURRENT, createTransactionDto.getCurrencyCode());
//
//    }
}
