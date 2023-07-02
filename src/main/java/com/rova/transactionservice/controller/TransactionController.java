package com.rova.transactionservice.controller;

import com.rova.transactionservice.dto.PageDto;
import com.rova.transactionservice.services.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping("/{accountReference}")
    public PageDto getUserAccountTransactions(@PathVariable String accountReference,
                                              @RequestParam(defaultValue = "0", required = false) int page,
                                              @RequestParam(defaultValue = "20", required = false) int size) {
        return transactionService.getUserAccountTransaction(1234, accountReference, page, size);
    }
}
