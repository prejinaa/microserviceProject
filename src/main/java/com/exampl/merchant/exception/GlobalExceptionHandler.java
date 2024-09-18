package com.exampl.merchant.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MerchantNotFound.class)
    public ResponseEntity<Object> handleMerchantNotFound(MerchantNotFound exception) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        responseBody.put("error", "Merchant Not Found");
        responseBody.put("message", exception.getMessage());
        responseBody.put("merchantId", exception.getMerchantId());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
}
