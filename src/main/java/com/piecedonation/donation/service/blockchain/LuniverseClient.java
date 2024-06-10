package com.piecedonation.donation.service.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LuniverseClient {

    private static final String ENVIRONMENT_ID = "environmentId";
    private static final String USER_KEY = "userKey";
    private static final String ACCESS_KEY = "accessKey";
    private static final String SECRET_KEY = "secretKey";
    private static final String EXPIRE = "expiresIn";

    private static final String BEARER = "Bearer";

    private static final String SERVICE_DEOA = "0x9ca179b9d6b58b4f93a02ee6d6d7f7057a6f9e0a";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.luniverse.environmentId}")
    private String environmentId;
    @Value("${spring.luniverse.authUri}")
    private String authUri;
    @Value("${spring.luniverse.accessKey}")
    private String accessKey;
    @Value("${spring.luniverse.secretKey}")
    private String secretKey;
    @Value("${spring.luniverse.walletUri}")
    private String walletUri;

    public String getLuniverseAuthToken() {
        HttpEntity<String> request = setAuthRequest();
        ResponseEntity<LuniverseAuthResponse> responseEntity = restTemplate.exchange(
                URI.create(authUri), HttpMethod.POST, request, LuniverseAuthResponse.class
        );

        return responseEntity.getBody().data().authToken().token();
    }

    private HttpEntity<String> setRequestBody(Map<String, Object> params, HttpHeaders headers) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = null;
        try {
            jsonBody = objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("블록체인 api 토큰을 요청할 수 없습니다. json매핑 오류");
        }

        return new HttpEntity<>(jsonBody, headers);
    }

    private HttpEntity<String> setAuthRequest() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ACCESS_KEY, accessKey);
        paramMap.put(SECRET_KEY, secretKey);
        paramMap.put(EXPIRE, 604800);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return setRequestBody(paramMap, headers);
    }

    public WalletData createAccount(String memberId) {
        HttpEntity<String> walletRequest = setWalletRequest(memberId);

        ResponseEntity<WalletResponse> responseEntity = restTemplate.exchange(
                URI.create(walletUri), HttpMethod.POST, walletRequest, WalletResponse.class
        );

        return responseEntity.getBody().data();
    }

    private HttpEntity<String> setWalletRequest(String userKey) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ENVIRONMENT_ID, environmentId);
        paramMap.put(USER_KEY, userKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        return setRequestBody(paramMap, headers);
    }

    public void transferTokenToMemberWallet(String memberWalletAddress) {
        String tokenValue = "1";
        approveSpender(tokenValue);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("txId", UUID.randomUUID().toString());
        requestBody.put("from", SERVICE_DEOA);

        Map<String, String> inputs = new HashMap<>();
        inputs.put("_from", SERVICE_DEOA);
        inputs.put("_to", memberWalletAddress);
        inputs.put("_value", tokenValue);
        requestBody.put("inputs", inputs);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        String url = "https://console-api.lambda256.io/tx/v2.0/transactions/transferLMT";
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    private void approveSpender(String value) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("txId", UUID.randomUUID().toString());
        requestBody.put("from", SERVICE_DEOA);

        Map<String, String> inputs = new HashMap<>();
        inputs.put("_spender", SERVICE_DEOA);
        inputs.put("_value", value);
        requestBody.put("inputs", inputs);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String url = "https://console-api.lambda256.io/tx/v2.0/transactions/approveServiceDEOAoutput";
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

}
