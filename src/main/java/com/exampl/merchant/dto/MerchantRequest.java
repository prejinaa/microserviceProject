package com.exampl.merchant.dto;

public record MerchantRequest(
        String businessName,
        String businessAddress,
        Long contactNumber,
        String email,
        Long userId
) {
  }
