package com.exampl.merchant.controller;

import com.exampl.merchant.dto.MerchantRequest;
import com.exampl.merchant.dto.MerchantResponse;
import com.exampl.merchant.dto.MerchantWithAccountResponse;
import com.exampl.merchant.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping()

    private ResponseEntity<List<MerchantResponse >>getALlMerchant(){
    return new ResponseEntity<>(merchantService.getALlMerchant(),HttpStatus.OK);
    }

    @PostMapping()

    private ResponseEntity<MerchantResponse> createMerchant(@RequestBody MerchantRequest merchantRequest){
          MerchantResponse merchantResponse= merchantService.createMerchant(merchantRequest);
          return  new ResponseEntity<>(merchantResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{merchantId}")

    private ResponseEntity<MerchantWithAccountResponse> getMerchantById(@PathVariable Long merchantId){
        MerchantWithAccountResponse merchantWithAccountResponse= merchantService.getMerchantById(merchantId);
        return  new ResponseEntity<>(merchantWithAccountResponse, HttpStatus.OK);
    }
    @PutMapping("/{merchantId}")

    private ResponseEntity<MerchantResponse>updateMerchant(@RequestBody MerchantRequest merchantRequest,@PathVariable Long merchantId){
                        MerchantResponse merchantResponse= merchantService.updateMerchant(merchantRequest,merchantId);
                        return new ResponseEntity<>(merchantResponse,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    private ResponseEntity<List<MerchantWithAccountResponse>>getMerchantByUserID(@PathVariable Long userId){
        List<MerchantWithAccountResponse> merchantResponse= merchantService.getMerchantByUserID(userId);
        return  new ResponseEntity<>(merchantResponse, HttpStatus.OK);
    }
}
