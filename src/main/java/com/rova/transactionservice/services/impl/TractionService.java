package com.rova.transactionservice.services.impl;

import com.rova.transactionservice.dals.Currency;
import com.rova.transactionservice.dals.Transaction;
import com.rova.transactionservice.dto.CreateTransactionDto;
import com.rova.transactionservice.dto.PageDto;
import com.rova.transactionservice.dto.TransactionDto;
import com.rova.transactionservice.exceptions.NotFoundException;
import com.rova.transactionservice.repository.TransactionsRepository;
import com.rova.transactionservice.services.ICurrencyService;
import com.rova.transactionservice.services.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TractionService implements ITransactionService {

    private final TransactionsRepository repository;

    private final ICurrencyService iCurrencyService;

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
}
