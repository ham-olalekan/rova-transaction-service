package com.rova.transactionservice.dto;

import com.rova.transactionservice.enums.WalletType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateWalletDto {
    @NotEmpty(message = "Amount is required")
    @DecimalMin(value = "0", message = "Amount must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Invalid amount format")
    private BigDecimal amount;

    private WalletType type;

    @NotEmpty(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters long")
    private String currencyCode;

    @NotNull(message = "Currency code is required")
    @Size(min = 2, max = 2, message = "Currency code must be exactly 2 characters long")
    private String countryCode;

    @NotNull(message = "first name is required")
    @Size(min = 2, max = 100, message = "first name must be minimum of 2 character and max length 100")
    private String firstName;

    @NotNull(message = "last name is required")
    @Size(min = 2, max = 100, message = "last name must be minimum of 2 character and max length 100")
    private String lastName;
}
