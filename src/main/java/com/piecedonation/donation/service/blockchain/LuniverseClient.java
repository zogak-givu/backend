package com.piecedonation.donation.service.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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
    @Value("${spring.luniverse.traceProgramId}")
    private String traceProgramId;

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

        System.out.println(walletUri);
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

    public void transferTokenToMemberWallet() {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("txId", UUID.randomUUID().toString());
        requestBody.put("from", "0xe3f7dcc925bde056b3859924f642d9d6d4ae2d63");

        Map<String, String> inputs = new HashMap<>();
        inputs.put("_to", "0x83314822c5d405f66666ab349d2c3d78d4fbc27e");
        inputs.put("_value", "100");
        requestBody.put("inputs", inputs);

        // HttpEntity 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 요청 URL
        String url = "https://console-api.lambda256.io/tx/v2.0/transactions/JOGAKEOA2DEOA";

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 응답 출력
        System.out.println(response.getBody());
        createEvent("piece_don_to_user", "testTx", "100");
    }

    private void createEvent(String memberId, String txId, String value) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getLuniverseAuthToken());
        headers.set("Content-Type", "application/json");

        // 요청 바디 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("eventName", "Create");
        requestBody.put("userName", "");
        requestBody.put("objectId", "user_pay_token");
        requestBody.put("timestamp", System.currentTimeMillis() / 1000);
        requestBody.put("data", "member:" + memberId + " txId:" + txId + " value:" + value); // inputData는 JSON 문자열로 변환되어야 할 수도 있습니다.

        // HttpEntity 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.luniverse.io/svc/v2/neptune/trace-programs/" + traceProgramId + "/events",
                HttpMethod.POST,
                entity,
                String.class);

    }

    public String findHistory(String accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", BEARER + " " + getLuniverseAuthToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "1");

        String baseUrl = "https://web3.luniverse.io/v1/aptos/sepolia";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                        baseUrl + "/accounts/{address}/transactions"
                                .replace("{address}", "x"))
                .queryParams(queryParams);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        System.out.println(response.getBody());
        return response.getBody();
        //안됨
    }

    public String getTxHistory(String txId) throws IOException {
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            String url = "https://api.luniverse.io/tx/v2.0/transactions";
//            HttpGetWithEntity getRequest = new HttpGetWithEntity(url);
//
//            String json = "{\"environmentId\":\"1712389165551188574\",\"txId\":\"yourTxId\"}";
//            StringEntity entity = new StringEntity(json);
//            getRequest.setEntity(entity);
//
//            getRequest.setHeader("accept", "application/json");
//            getRequest.setHeader("content-type", "application/json");
//            getRequest.setHeader("Authorization", "Bearer " + getLuniverseAuthToken());
//
//            try (CloseableHttpResponse response = client.execute(getRequest)) {
//                String responseBody = EntityUtils.toString(response.getEntity());
//                System.out.println(responseBody);
//            }
//        }

        OkHttpClient client = new OkHttpClient();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        String authToken = getLuniverseAuthToken();
        System.out.println(authToken);

        RequestBody body = RequestBody.create(mediaType, "{\"environmentId\":\"환경ID\"}");
        Request request = new Request.Builder()
                .url("https://api.luniverse.io/tx/v2.0/transactions")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", BEARER +" "+authToken)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().string();

        ////
//        OkHttpClient client = new OkHttpClient();
//
//        okhttp3.MediaType JSON = okhttp3.MediaType.parse("application/json; charset=utf-8");
//
//        // requestBody를 JSON 문자열로 생성합니다. 실제 값으로 대체해야 합니다.
//        String json = "{\"environmentId\":\"" + environmentId + "\",\"txId\":\"" + txId + "\"}";
//        RequestBody body = RequestBody.create(json, JSON);
//
//        Request request = new Request.Builder()
//                .url("https://api.luniverse.io/tx/v2.0/transactions")
//                .method("GET", body) // OkHttp는 기술적으로 GET 요청에 requestBody를 허용합니다.
//                .addHeader("accept", "application/json")
//                .addHeader("content-type", "application/json")
//                .addHeader("Authorization", "Bearer " + getLuniverseAuthToken())
//                .build();
//
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
//
//        return response.body().string();

//        // HttpHeaders 객체 생성 및 헤더 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("accept", "application/json");
//        headers.add("content-type", "application/json");
//        headers.add("Authorization", BEARER+" "+getLuniverseAuthToken());
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("environmentId", environmentId);
//        requestBody.put("txId", txId);
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        // exchange() 메소드를 사용하여 요청 보내기
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://api.luniverse.io/tx/v2.0/transactions",
//                HttpMethod.GET,
//                entity,
//                String.class);
//
//        // 응답 출력
//        System.out.println(response.getBody());
//        return response.getBody();
    }
}

