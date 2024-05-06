package com.piecedonation.donation.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KakaoOAuthClient extends OAuthClient{

    @Value("${spring.auth.kakao.tokenUri}")
    private String kakaoTokenUri;
    @Value("${spring.auth.kakao.clientId}")
    private String clientId;
    @Value("${spring.auth.kakao.clientSecret}")
    private String clientSecret;
    @Value("${spring.auth.kakao.redirectUri}")
    private String redirectUri;

    @Override
    protected String clientId() {
        return clientId;
    }

    @Override
    protected String clientSecret() {
        return clientSecret;
    }

    @Override
    protected String redirectUri() {
        return redirectUri;
    }

    @Override
    protected String tokenUri() {
        return kakaoTokenUri;
    }
}
