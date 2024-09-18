package com.exampl.merchant.dto;
import java.time.LocalDateTime;

public record AccountResponse(
        Long accountId,
        String accountNumber,
        Double balance,
        AccountType accountType,
        LocalDateTime createDate) {
}
