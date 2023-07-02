package com.rova.transactionservice.services;

import com.rova.transactionservice.dals.Currency;

import java.util.Optional;

public interface ICurrencyService {
    Optional<Currency> findByCountryCodeAndCurrencyCode(String countryCode, String currencyCode);
}
