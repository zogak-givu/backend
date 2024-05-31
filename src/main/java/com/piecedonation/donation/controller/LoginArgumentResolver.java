package com.piecedonation.donation.controller;

import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.MemberRepository;
import com.piecedonation.donation.service.auth.AuthService;
import io.jsonwebtoken.JwtException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final AuthService authService;
    private final MemberRepository memberRepository;

    public LoginArgumentResolver(AuthService authService, MemberRepository memberRepository) {
        this.authService = authService;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = parseTokenFrom(webRequest);

        authService.validate(token);
        return memberRepository.findById(authService.getMemberId(token))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
    }

    private String parseTokenFrom(NativeWebRequest webRequest) {
        String value = webRequest.getHeader(AUTHORIZATION);
        if (value == null) {
            throw new JwtException("토큰이 존재하지 않습니다");
        }

        return value.replace(BEARER, "");
    }

}
