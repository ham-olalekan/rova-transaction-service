package com.rova.transactionservice.repository;

import com.rova.transactionservice.dals.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletsRepository extends CrudRepository<Wallet, Long> {
    @Query(value = "SELECT * FROM wallets WHERE user_id=?1 AND reference=?2", nativeQuery = true)
    Optional<Wallet> findByUserIdAndReference(long userId, String reference);
}
