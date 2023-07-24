package com.rova.transactionservice.services;

import com.rova.transactionservice.dals.Wallet;
import com.rova.transactionservice.dto.CreateWalletDto;
import com.rova.transactionservice.dto.WalletDto;
import com.rova.transactionservice.enums.WalletType;
import com.rova.transactionservice.exceptions.CommonsException;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.InsufficientFundsException;
import com.rova.transactionservice.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

public interface IWalletService {
    Optional<Wallet> getWallet(long userId, WalletType type, String currencyCode);

    WalletDto createWallet(long userId, CreateWalletDto createWalletDto) throws NotFoundException, DuplicateRequestException;

    WalletDto getWalletBalance(long userId, String reference) throws NotFoundException;

    WalletDto debitWallet(String transactionReference, String walletReference, BigDecimal amount)
            throws NotFoundException, InsufficientFundsException, CommonsException;

    WalletDto creditWallet(String transactionReference, String walletReference, BigDecimal amount) throws NotFoundException, CommonsException;
}
