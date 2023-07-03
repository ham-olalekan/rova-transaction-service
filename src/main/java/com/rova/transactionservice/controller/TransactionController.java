package com.rova.transactionservice.controller;

import com.rova.transactionservice.dals.IUserDetails;
import com.rova.transactionservice.dto.PageDto;
import com.rova.transactionservice.services.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.rova.transactionservice.enums.Authorities.USER_PREAUTHORIZE;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@PreAuthorize(USER_PREAUTHORIZE)
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping("/{accountReference}")
    public PageDto getUserAccountTransactions(Authentication authentication,
                                              @PathVariable String accountReference,
                                              @RequestParam(defaultValue = "0", required = false) int page,
                                              @RequestParam(defaultValue = "20", required = false) int size) {
        long userId = IUserDetails.getId(authentication);
        return transactionService.getUserAccountTransaction(userId, accountReference, page, size);
    }
}
