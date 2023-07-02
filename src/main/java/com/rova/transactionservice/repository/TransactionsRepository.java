package com.rova.transactionservice.repository;

import com.rova.transactionservice.dals.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transactions t " +
            "JOIN wallets w ON w.id = t.wallet_id " +
            "WHERE w.reference = ?2 AND t.user_id = ?1 " +
            "ORDER BY t.created_at",
            countQuery = "SELECT COUNT(*) FROM transactions t " +
                    "JOIN wallets w ON w.id = t.wallet_id " +
                    "WHERE w.reference = ?2 AND t.user_id = ?1",
            nativeQuery = true)
    Page<Transaction> findByUserIdAndAccountReference(long userId, String accountReference, Pageable pageable);
}
