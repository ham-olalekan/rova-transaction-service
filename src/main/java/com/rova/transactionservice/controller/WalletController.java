package com.rova.transactionservice.controller;

import com.rova.transactionservice.dto.CreateWalletDto;
import com.rova.transactionservice.dto.ResponseDto;
import com.rova.transactionservice.dto.WalletDto;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.NotFoundException;
import com.rova.transactionservice.services.IWalletService;
import com.rova.transactionservice.services.IdempotencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/accounts")
@RequiredArgsConstructor
public class WalletController {

    private final IWalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<WalletDto> createAccount(@Valid @RequestBody CreateWalletDto createWalletDto) throws NotFoundException, DuplicateRequestException {
        WalletDto dto = walletService.createWallet(1234, createWalletDto);
        return ResponseDto.wrapSuccessResult(dto, "account created successfully");
    }

    @GetMapping("/{reference}/balance")
    public ResponseDto<WalletDto> handleAccountBalance(@PathVariable String reference) throws NotFoundException {
        WalletDto dto = walletService.getWalletBalance(1234, reference);
        return ResponseDto.wrapSuccessResult(dto, "account balance fetched successfully");
    }
}
