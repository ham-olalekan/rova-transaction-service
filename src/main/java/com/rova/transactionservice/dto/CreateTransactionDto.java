package com.rova.transactionservice.dto;

import com.rova.transactionservice.enums.TransactionType;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class CreateTransactionDto {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "50", message = "Amount must be greater than or equal to 50")
    @Digits(integer = 10, fraction = 2, message = "Invalid amount format")
    private BigDecimal amount;

    private TransactionType type;

    private String remark;

    @NotNull(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 2 characters long")
    private String currencyCode;

    @NotNull(message = "Currency code is required")
    @Size(min = 2, max = 2, message = "Currency code must be exactly 2 characters long")
    private String countryCode;
}
