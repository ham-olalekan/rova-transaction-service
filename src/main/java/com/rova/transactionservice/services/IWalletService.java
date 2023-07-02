package com.rova.transactionservice.services;

import com.rova.transactionservice.dto.CreateWalletDto;
import com.rova.transactionservice.dto.WalletDto;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.NotFoundException;

public interface IWalletService {
    WalletDto createWallet(long userId, CreateWalletDto createWalletDto) throws NotFoundException, DuplicateRequestException;
    WalletDto getWalletBalance(long userId, String reference) throws NotFoundException;
}
