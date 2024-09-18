package com.exampl.merchant.service;
import com.exampl.merchant.dto.AccountResponse;
import com.exampl.merchant.dto.MerchantRequest;
import com.exampl.merchant.dto.MerchantResponse;
import com.exampl.merchant.dto.MerchantWithAccountResponse;
import com.exampl.merchant.exception.MerchantNotFound;
import com.exampl.merchant.model.Merchant;
import com.exampl.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final WebClient webClient;


    public List<MerchantResponse> getALlMerchant(){
       return merchantRepository.findAllBy();

    }

    public MerchantResponse createMerchant(MerchantRequest merchantRequest){
        Merchant merchant=new Merchant();
        merchant.setBusinessName(merchantRequest.businessName());
        merchant.setBusinessAddress(merchantRequest.businessAddress());
        merchant.setContactNumber(merchantRequest.contactNumber());
        merchant.setEmail(merchantRequest.email());
        merchant.setUserId(merchantRequest.userId());
       Merchant saveMerchant= merchantRepository.save(merchant);
        return new MerchantResponse(
                saveMerchant.getMerchantId(),
                saveMerchant.getBusinessName(),
                saveMerchant.getBusinessAddress(),
                saveMerchant.getContactNumber(),
                saveMerchant.getEmail(),
                saveMerchant.getUserId(),
                saveMerchant.getCreateDate(),
                saveMerchant.getLastModified()
        );
    }

    public MerchantWithAccountResponse getMerchantById(Long merchantId) throws MerchantNotFound {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFound(merchantId));

       List<AccountResponse> accountResponse = webClient.get()
                .uri("http://localhost:8083/api/accounts/merchant/" + merchantId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AccountResponse>>() {})
                .onErrorResume(e -> {
                    return Mono.empty();
                })
                .block();

        return new  MerchantWithAccountResponse(
                merchant.getMerchantId(),
                merchant.getBusinessName(),
                merchant.getBusinessAddress(),
                merchant.getContactNumber(),
                merchant.getEmail(),
                merchant.getUserId(),
                merchant.getCreateDate(),
                merchant.getLastModified(),
                accountResponse
        );

    }

    public MerchantResponse updateMerchant(MerchantRequest merchantRequest, Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFound(merchantId));
        merchant.setBusinessName(merchantRequest.businessName());
        merchant.setBusinessAddress(merchantRequest.businessAddress());
        merchant.setContactNumber(merchantRequest.contactNumber());
        merchant.setEmail(merchantRequest.email());
        merchant.setUserId(merchantRequest.userId());
        merchantRepository.save(merchant);

        return new MerchantResponse(
                merchant.getMerchantId(),
                merchant.getBusinessName(),
                merchant.getBusinessAddress(),
                merchant.getContactNumber(),
                merchant.getEmail(),
                merchant.getUserId(),
                merchant.getCreateDate(),
                merchant.getLastModified()
        );

    }

    public List<MerchantWithAccountResponse> getMerchantByUserID(Long userId) {
        List<Merchant> merchants = merchantRepository.findByUserId(userId);

        if (merchants.isEmpty()) {
            throw new RuntimeException("No merchants found for userId: " + userId);
        }

        return merchants.stream()
                .map(merchant -> {

                    List<AccountResponse> accountResponses = webClient.get()
                            .uri("http://localhost:8083/api/accounts/merchant/" + merchant.getMerchantId())
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<List<AccountResponse>>() {})
                            .onErrorResume(e -> Mono.empty())
                            .block();
                    return new MerchantWithAccountResponse(
                            merchant.getMerchantId(),
                            merchant.getBusinessName(),
                            merchant.getBusinessAddress(),
                            merchant.getContactNumber(),
                            merchant.getEmail(),
                            merchant.getUserId(),
                            merchant.getCreateDate(),
                            merchant.getLastModified(),
                            accountResponses
                    );
                })

                .collect(Collectors.toList());
    }



}



