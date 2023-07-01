package com.rova.transactionservice.repository;

import com.rova.transactionservice.dals.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletsRepository extends CrudRepository<Wallet, Long> {
}
