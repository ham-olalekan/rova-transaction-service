package com.rova.transactionservice.services.impl;

import com.rova.transactionservice.dals.Currency;
import com.rova.transactionservice.repository.CurrencyRepository;
import com.rova.transactionservice.services.ICurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {

    private final CurrencyRepository repository;

    @Override
    public Optional<Currency> findByCountryCodeAndCurrencyCode(String countryCode, String currencyCode) {
        return repository.findByCountryCodeAndCurrencyCode(countryCode, currencyCode);
    }
}
