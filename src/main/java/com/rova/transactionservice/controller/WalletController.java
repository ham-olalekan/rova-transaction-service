package com.rova.transactionservice.controller;

import com.rova.transactionservice.dals.IUserDetails;
import com.rova.transactionservice.dto.CreateWalletDto;
import com.rova.transactionservice.dto.ResponseDto;
import com.rova.transactionservice.dto.WalletDto;
import com.rova.transactionservice.exceptions.DuplicateRequestException;
import com.rova.transactionservice.exceptions.NotFoundException;
import com.rova.transactionservice.services.IWalletService;
import com.rova.transactionservice.services.IdempotencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.rova.transactionservice.enums.Authorities.USER_PREAUTHORIZE;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@PreAuthorize(USER_PREAUTHORIZE)
public class WalletController {

    private final IWalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<WalletDto> createAccount(Authentication authentication,
                                                @Valid @RequestBody CreateWalletDto createWalletDto) throws NotFoundException, DuplicateRequestException {
        long userId = IUserDetails.getId(authentication);
        WalletDto dto = walletService.createWallet(userId, createWalletDto);
        return ResponseDto.wrapSuccessResult(dto, "account created successfully");
    }

    @GetMapping("/{reference}/balance")
    public ResponseDto<WalletDto> handleAccountBalance(Authentication authentication,@PathVariable String reference) throws NotFoundException {
        long userId = IUserDetails.getId(authentication);
        WalletDto dto = walletService.getWalletBalance(userId, reference);
        return ResponseDto.wrapSuccessResult(dto, "account balance fetched successfully");
    }
}
