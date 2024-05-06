package com.piecedonation.donation.service.auth;

import com.piecedonation.donation.controller.TokenResponse;
import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.MemberRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Transactional
public class AuthService {

    private final SecretKey key;
    private final long accessTokenExpired;
    private final long refreshTokenExpired;

    private final MemberRepository memberRepository;
    private final OAuthClient oauthClient;

    public AuthService(@Value("${spring.auth.key}") String key,
                       @Value("${spring.auth.accessTokenExpired}") long accessTokenExpired,
                       @Value("${spring.auth.refreshTokenExpired}") long refreshTokenExpired,
                       MemberRepository memberRepository,
                       OAuthClient oauthClient) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpired = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
        this.memberRepository = memberRepository;
        this.oauthClient = oauthClient;
    }

    public TokenResponse createAccessToken(String code) {
        MemberInfo memberInfo = oauthClient.getMemberInfo(code);
        Member member = memberRepository.findById(memberInfo.openId())
                .orElseGet(() -> memberRepository.save(new Member(memberInfo.openId(), memberInfo.name())));

        return new TokenResponse(accessTokenFromMember(member.getId()));
    }

    private String accessTokenFromMember(String memberId) {
        return builder(accessTokenExpired)
                .setSubject(memberId)
                .compact();
    }

    private JwtBuilder builder(final long expired) {
        final Date validity = new Date(System.currentTimeMillis() + expired);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256);
    }
}
