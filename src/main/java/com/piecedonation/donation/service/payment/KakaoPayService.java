package com.piecedonation.donation.service.payment;

import com.piecedonation.donation.domain.KakaoPayProperties;
import com.piecedonation.donation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    public KakaoPayReadyResponse getKakaoPayReady(KakaoPayReadyRequest request) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(getReadyParameters(request), getHeaders());
        try {
            RestTemplate restTemplate = new RestTemplate();
            KakaoPayReadyResponse response = restTemplate.postForObject(
                    KakaoPayProperties.readyUrl,
                    requestEntity,
                    KakaoPayReadyResponse.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "KakaoAK " + KakaoPayProperties.adminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    private MultiValueMap<String, String> getReadyParameters(KakaoPayReadyRequest request) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", KakaoPayProperties.cid);
        parameters.add("partner_order_id", request.getPartner_order_id());
        parameters.add("partner_user_id", request.getPartner_user_id());
        parameters.add("item_name", request.getItem_name());
        parameters.add("quantity", String.valueOf(request.getQuantity()));
        parameters.add("total_amount", String.valueOf(request.getTotal_amount()));
        parameters.add("tax_free_amount", String.valueOf(request.getTax_free_amount()));
        parameters.add("approval_url", request.getApproval_url());
        parameters.add("cancel_url", request.getCancel_url());
        parameters.add("fail_url", request.getFail_url());

        return parameters;
    }

    public KakaoPayApproveResponse getKakaoPayApprove(KakaoPayApproveRequest request) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(this.getApproveParameters(request), this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        try {
            KakaoPayApproveResponse response = restTemplate.postForObject(
                    KakaoPayProperties.approveUrl,
                    requestEntity,
                    KakaoPayApproveResponse.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    private MultiValueMap<String, String> getApproveParameters(KakaoPayApproveRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", KakaoPayProperties.cid);
        parameters.add("partner_order_id", request.getPartner_order_id());
        parameters.add("partner_user_id", request.getPartner_user_id());
        parameters.add("tid", request.getTid());
        parameters.add("pg_token", request.getPg_token());

        return parameters;
    }


    public KakaoPayCancleResponse getkakaoPayCancel(KaKaoPayCancleRequest request) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(getCancleParameters(request), getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        try {
            KakaoPayCancleResponse response = restTemplate.postForObject(
                    KakaoPayProperties.cancleUrl,
                    requestEntity,
                    KakaoPayCancleResponse.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    private MultiValueMap<String, String> getCancleParameters(KaKaoPayCancleRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", KakaoPayProperties.cid);
        parameters.add("tid", request.getTid());
        parameters.add("cancel_amount", String.valueOf(request.getCancel_amount()));
        parameters.add("cancel_tax_free_amount", String.valueOf(request.getCancel_tax_free_amount()));

        return parameters;
    }
}