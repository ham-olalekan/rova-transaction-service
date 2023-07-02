package com.rova.transactionservice.repository;

import com.rova.transactionservice.dals.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    @Query(value = "SELECT * FROM currencies WHERE country_code=?1 AND currency_code=?2", nativeQuery = true)
    Optional<Currency> findByCountryCodeAndCurrencyCode(String countryCode, String currencyCode);
}
