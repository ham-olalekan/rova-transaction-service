package com.rova.transactionservice.services;

import com.rova.transactionservice.dals.Transaction;
import com.rova.transactionservice.dto.CreateTransactionDto;
import com.rova.transactionservice.dto.PageDto;
import com.rova.transactionservice.dto.TransactionDto;
import com.rova.transactionservice.enums.TransactionStatus;
import com.rova.transactionservice.exceptions.CommonsException;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITransactionService {
    TransactionDto createTransaction(long userId, CreateTransactionDto createTransactionDto) throws NotFoundException;

    TransactionDto save(Transaction transaction);

    PageDto getUserAccountTransaction(long userId, String accountReference, int page, int size);

   // TransactionDto createTransaction(CreateTransactionDto createTransactionDto) throws NotFoundException, CommonsException, DuplicateRequestException;

}
