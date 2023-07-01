package com.rova.transactionservice.services;

import com.rova.transactionservice.dto.TransactionDto;
import com.rova.transactionservice.enums.TransactionStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITransactionService {
    TransactionDto createTransaction();

    Page<TransactionDto> getUserTransactions(long userId, int page, int size, List<TransactionStatus> statuses);
}
