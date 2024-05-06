package com.piecedonation.donation.controller;

import com.piecedonation.donation.service.auth.KakaoOAuthClient;
import com.piecedonation.donation.service.auth.MemberInfo;
import com.piecedonation.donation.service.blockchain.LuniverseClient;
import com.piecedonation.donation.service.blockchain.WalletData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthControllerTest {

    @LocalServerPort
    private int port;
    @SpyBean
    KakaoOAuthClient oAuthClient;
    @SpyBean
    LuniverseClient luniverseClient;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 로그인을_요청하면_토큰을_생성한다() {
        //given
        final String testAuthCode = "testAuthCode";
        doReturn(new MemberInfo("id", "name"))
                .when(oAuthClient).getMemberInfo(anyString());
        doReturn(new WalletData("walletId", "address"))
                .when(luniverseClient).createAccount(anyString());

        //when
        final Response response = RestAssured.
                given().log().all()
                    .post("/auth?code="+testAuthCode)
                .then().log().all()
                .extract().response();

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(response.jsonPath().getString("token")).isNotEmpty();
        });
    }
}