package com.piecedonation.donation.service.payment;

import com.piecedonation.donation.domain.KakaoPayProperties;
import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.dto.KaKaoPayCancleRequest;
import com.piecedonation.donation.dto.KakaoPayApproveRequest;
import com.piecedonation.donation.dto.KakaoPayApproveResponse;
import com.piecedonation.donation.dto.KakaoPayCancleResponse;
import com.piecedonation.donation.dto.KakaoPayReadyRequest;
import com.piecedonation.donation.dto.KakaoPayReadyResponse;
import com.piecedonation.donation.service.blockchain.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoPayProperties kakaoPayProperties;
    private final WalletService walletService;

    public KakaoPayReadyResponse getKakaoPayReady(KakaoPayReadyRequest request) {
        HttpEntity<HashMap<String, Object>> requestEntity = new HttpEntity<>(getReadyParameters(request), getHeaders());
        try {
            ResponseEntity<KakaoPayReadyResponse> response = restTemplate.exchange(
                    kakaoPayProperties.getReadyUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    KakaoPayReadyResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "SECRET_KEY " + kakaoPayProperties.getSecretKey();

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }

    private HashMap<String, Object> getReadyParameters(KakaoPayReadyRequest request) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("cid", kakaoPayProperties.getCid());
        parameters.put("partner_order_id", request.getPartner_order_id());
        parameters.put("partner_user_id", request.getPartner_user_id());
        parameters.put("item_name", request.getItem_name());
        parameters.put("quantity", request.getQuantity());
        parameters.put("total_amount", request.getTotal_amount());
        parameters.put("tax_free_amount", request.getTax_free_amount());
        parameters.put("approval_url", request.getApproval_url());
        parameters.put("cancel_url", request.getCancel_url());
        parameters.put("fail_url", request.getFail_url());

        return parameters;
    }

    public KakaoPayApproveResponse getKakaoPayApprove(KakaoPayApproveRequest request, Member member) {
        HttpEntity<HashMap<String, String>> requestEntity = new HttpEntity<>(this.getApproveParameters(request), this.getHeaders());

        try {
            HttpEntity<KakaoPayApproveResponse> response = restTemplate.exchange(
                    kakaoPayProperties.getApproveUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    KakaoPayApproveResponse.class
            );

            walletService.transferTokenToMemberWallet(member, request.getOrganization_name());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    private HashMap<String, String> getApproveParameters(KakaoPayApproveRequest request) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("cid", kakaoPayProperties.getCid());
        parameters.put("partner_order_id", request.getPartner_order_id());
        parameters.put("partner_user_id", request.getPartner_user_id());
        parameters.put("tid", request.getTid());
        parameters.put("pg_token", request.getPg_token());

        return parameters;
    }


    public KakaoPayCancleResponse getkakaoPayCancel(KaKaoPayCancleRequest request) {
        HttpEntity<HashMap<String, String>> requestEntity = new HttpEntity<>(getCancleParameters(request), getHeaders());

        try {
             return restTemplate.postForObject(
                    kakaoPayProperties.getCancleUrl(),
                    requestEntity,
                    KakaoPayCancleResponse.class
            );

        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    private HashMap<String, String> getCancleParameters(KaKaoPayCancleRequest request) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("cid", kakaoPayProperties.getCid());
        parameters.put("tid", request.getTid());
        parameters.put("cancel_amount", String.valueOf(request.getCancel_amount()));
        parameters.put("cancel_tax_free_amount", String.valueOf(request.getCancel_tax_free_amount()));

        return parameters;
    }
}
