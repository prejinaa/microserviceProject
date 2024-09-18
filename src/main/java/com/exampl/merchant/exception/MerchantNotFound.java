package com.exampl.merchant.exception;
import lombok.Getter;

@Getter
public class MerchantNotFound extends RuntimeException {
    private final Long merchantId;

    public MerchantNotFound (Long merchantId) {
        super("Merchant with Id " + merchantId+ " not found");
        this.merchantId=merchantId;
    }
}
