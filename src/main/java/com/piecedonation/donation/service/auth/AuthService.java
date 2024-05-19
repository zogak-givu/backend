package com.piecedonation.donation.service.auth;

import com.piecedonation.donation.controller.TokenResponse;
import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.MemberRepository;
import com.piecedonation.donation.service.blockchain.WalletService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
@Transactional(readOnly = true)
public class AuthService {

    private final SecretKey key;
    private final long accessTokenExpired;
    private final long refreshTokenExpired;

    private final WalletService walletService;
    private final MemberRepository memberRepository;
    private final OAuthClient oauthClient;

    public AuthService(@Value("${spring.auth.key}") String key,
                       @Value("${spring.auth.accessTokenExpired}") long accessTokenExpired,
                       @Value("${spring.auth.refreshTokenExpired}") long refreshTokenExpired,
                       WalletService walletService,
                       MemberRepository memberRepository,
                       OAuthClient oauthClient) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpired = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
        this.walletService = walletService;
        this.memberRepository = memberRepository;
        this.oauthClient = oauthClient;
    }

    @Transactional
    public TokenResponse createAccessToken(String code) {
        MemberInfo memberInfo = oauthClient.getMemberInfo(code);
        Member member = memberRepository.findById(memberInfo.openId())
                .orElseGet(() -> saveNewMember(new Member(memberInfo.openId(), memberInfo.name())));

        return new TokenResponse(accessTokenFromMember(member.getId()));
    }

    public Member saveNewMember(Member member) {
        Member savedMember = memberRepository.save(member);
        walletService.createWallet(savedMember);
        return savedMember;
    }

    private String accessTokenFromMember(String memberId) {
        return builder(accessTokenExpired)
                .setSubject(memberId)
                .compact();
    }

    private JwtBuilder builder(long expired) {
        Date validity = new Date(System.currentTimeMillis() + expired);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256);
    }

    public TokenResponse createRefreshToken() {
        String refreshToken = builder(refreshTokenExpired).compact();
        return new TokenResponse(refreshToken);
    }

    public TokenResponse refreshAccessToken(String access, String refresh) {
        validate(refresh);
        String memberId = toClaims(access).getSubject();
        String token = accessTokenFromMember(memberId);
        return new TokenResponse(token);
    }

    private void validate(String token) {
        Claims claims = toClaims(token);

        if (claims.getExpiration().before(new Date())) {
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }
    }

    private Claims toClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (final ExpiredJwtException expired) {
            return expired.getClaims();
        } catch (final Exception e) {
            throw new IllegalArgumentException("잘못된 토큰입니다.");
        }
    }
}
