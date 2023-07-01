package com.rova.transactionservice.repository;

import com.rova.transactionservice.dals.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends PagingAndSortingRepository<Transaction, Long> {
}
