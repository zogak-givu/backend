package com.piecedonation.donation.service.auth;

import com.piecedonation.donation.domain.OAuthToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

public abstract class OAuthClient {

    private static final String CODE = "code";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REDIRECT_URI = "redirect_uri";

    private static final RestTemplate restTemplate = new RestTemplate();

    public MemberInfo getMemberInfo(final String code) {
        final MultiValueMap<String, String> parameter = setParameters(code);

        OAuthToken oAuthToken = restTemplate.postForObject(URI.create(tokenUri()), parameter, OAuthToken.class);
        return Objects.requireNonNull(oAuthToken).toMemberInfo();
    }

    private MultiValueMap<String, String> setParameters(final String code) {
        final MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add(CODE, code);
        parameter.add(GRANT_TYPE, "authorization_code");
        parameter.add(CLIENT_ID, clientId());
        parameter.add(CLIENT_SECRET, clientSecret());
        parameter.add(REDIRECT_URI, redirectUri());

        return parameter;
    }

    protected abstract String clientId();

    protected abstract String clientSecret();

    protected abstract String redirectUri();

    protected abstract String tokenUri();

}

