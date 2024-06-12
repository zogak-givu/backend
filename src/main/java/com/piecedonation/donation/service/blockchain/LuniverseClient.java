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
    public static final String OWNER_USER_CONTRACT_ADDRESS = "0x29e6f746b4b45e171f10092e18e2060741389b2c";
    public static final String USER_CHARITY_CONTRACT_ADDRESS = "0x063d5dad1754c9959673e5c518c0ade61d8dfae3";

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

        String token = responseEntity.getBody().data().authToken().token();
        System.out.println(token);
        return token;
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

    public WalletData createAccount(String memberId) {
        HttpEntity<String> walletRequest = setWalletRequest(memberId);

        ResponseEntity<WalletResponse> responseEntity = restTemplate.exchange(
                URI.create(walletUri), HttpMethod.POST, walletRequest, WalletResponse.class
        );

        WalletData createdWallet = responseEntity.getBody().data();
        String defaultApproveAmount = "100";
        approveSpender(SERVICE_DEOA, OWNER_USER_CONTRACT_ADDRESS, defaultApproveAmount);
        approveSpender(createdWallet.address(), USER_CHARITY_CONTRACT_ADDRESS, defaultApproveAmount);

        return createdWallet;
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
        HttpEntity<Map<String, Object>> entity = setTransferRequest(memberWalletAddress);
        String url = "https://console-api.lambda256.io/tx/v2.0/transactions/transferLMT";
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    private HttpEntity<Map<String, Object>> setTransferRequest(String memberWalletAddress) {
        String tokenValue = "1";
        approveSpender(SERVICE_DEOA, memberWalletAddress, "10");

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

        return new HttpEntity<>(requestBody, headers);
    }

    private void approveSpender(String from, String spender, String value) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("txId", UUID.randomUUID().toString());
        requestBody.put("from", from);

        Map<String, String> inputs = new HashMap<>();
        inputs.put("_spender", spender);
        inputs.put("_value", value);
        requestBody.put("inputs", inputs);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String url = "https://console-api.lambda256.io/tx/v2.0/transactions/approveLST";
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public void executeContract(String user, String charity, String amount) {
        String approveAmount="10000";
        approveSpender(user, charity, approveAmount);
        approveSpender(SERVICE_DEOA, OWNER_USER_CONTRACT_ADDRESS, approveAmount);
        approveSpender(user, USER_CHARITY_CONTRACT_ADDRESS, approveAmount);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> inputs = new HashMap<>();
        inputs.put("owner", SERVICE_DEOA);
        inputs.put("user", user);
        inputs.put("charity", charity);
        inputs.put("amount", amount);
        inputs.put("id", String.valueOf(System.currentTimeMillis() / 1000));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("txId", UUID.randomUUID().toString());
        requestBody.put("from", SERVICE_DEOA);
        requestBody.put("inputs", inputs);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String url = "https://console-api.lambda256.io/tx/v2.0/transactions/executeDonateContract";

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(exchange.getBody());
    }

}
