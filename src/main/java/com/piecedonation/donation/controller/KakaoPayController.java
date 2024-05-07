package com.piecedonation.donation.controller;

import com.piecedonation.donation.service.payment.KakaoPayService;
import com.piecedonation.donation.dto.KakaoPayReadyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/kakao")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity readyKakaoPay(@RequestBody KakaoPayReadyRequest request) {
        return ResponseEntity.ok(kakaoPayService.getKakaoPayReady(request));
    }

}
