package com.piecedonation.donation.service.luniverse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LuniverseService {

    @Value("${luniverse.auth.token}")
    private String authToken;

    @Value("${luniverse.environmentId}")
    private String environmentId;

    @Value("${luniverse.wallet.from}")
    private String from;

    @Value("${luniverse.wallet.to}")
    private String to;

    @Value("${luniverse.txId}")
    private String txID;

    private HttpClient httpClient = HttpClient.newHttpClient();

    public String getBalance() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.luniverse.io/mx/v2.0/wallets/" + from + "/balance"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getNonce() throws Exception {
        String requestBody = String.format("{\"environmentId\":\"%s\"}", environmentId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.luniverse.io/mx/v2.0/wallets/" + from + "/nonce"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .method("GET", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String createDEOA() throws Exception {
        String requestBody = String.format("{\"environmentId\":\"%s\"}", environmentId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.luniverse.io/tx/v2.0/wallets"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    //error : can't write on history
    public String executeTransaction() throws Exception {
        String requestBody = String.format(
                "{\"txId\":\"%s\",\"from\":\"%s\",\"to\":\"%s\",\"inputs\":{\"_to\":\"%s\",\"_value\":\"30\"}}",
                txID, from, to, to
        );
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.luniverse.io/tx/v2.0/transactions/donation"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getTransactionHistory() throws Exception {
        String requestBody = String.format("{\"environmentId\":\"%s\",\"txId\":\"%s\"}", environmentId, txID);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.luniverse.io/tx/v2.0/transactions"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .method("GET", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
