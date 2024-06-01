package com.piecedonation.donation.service.auth;

import com.piecedonation.donation.controller.TokenResponse;
import com.piecedonation.donation.domain.MemberRepository;
import com.piecedonation.donation.service.blockchain.LuniverseClient;
import com.piecedonation.donation.service.blockchain.WalletData;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    AuthService authService;
    @Autowired
    MemberRepository memberRepository;
    @SpyBean
    KakaoOAuthClient kakaoOAuthClient;
    @SpyBean
    LuniverseClient luniverseClient;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void 인증코드로_회원가입한다() {
        //given
        doReturn(new MemberInfo("testOpenId", "testName"))
                .when(kakaoOAuthClient).getMemberInfo(anyString());
        doReturn(new WalletData("walletId", "address"))
                .when(luniverseClient).createAccount(anyString());

        //when
        TokenResponse tokenResponse = authService.createAccessToken("testAuthCode");

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(tokenResponse.token()).isNotEmpty();
            assertThat(memberRepository.findAll()).hasSize(1);
        });
    }
}