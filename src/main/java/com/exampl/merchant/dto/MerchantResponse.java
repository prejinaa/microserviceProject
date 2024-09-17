package com.exampl.merchant.dto;

public record MerchantResponse<accountResponse>(
        Long merchantId,
        String businessName,
        String businessAddress,
        Long contactNumber,
        String email,
        AccountResponse accountResponse


) {
}
