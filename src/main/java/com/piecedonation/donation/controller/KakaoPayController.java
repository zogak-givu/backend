package com.piecedonation.donation.controller;

import com.piecedonation.donation.dto.KaKaoPayCancleRequest;
import com.piecedonation.donation.dto.KakaoPayApproveRequest;
import com.piecedonation.donation.dto.KakaoPayReadyRequest;
import com.piecedonation.donation.service.payment.KakaoPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/kakao")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    public KakaoPayController(KakaoPayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity readyKakaoPay(@RequestBody KakaoPayReadyRequest request) {
        return ResponseEntity.ok(kakaoPayService.getKakaoPayReady(request));
    }

    /**
     * 결제 성공
     */
    @PostMapping("/success")
    public ResponseEntity afterPayRequest(@RequestBody KakaoPayApproveRequest request) {
        return ResponseEntity.ok(kakaoPayService.getKakaoPayApprove(request));
    }
    /**
     * 환불
     */
    @PostMapping("/refund")
    public ResponseEntity refund(@RequestBody KaKaoPayCancleRequest request) {
        return ResponseEntity.ok(kakaoPayService.getkakaoPayCancel(request));
    }

}
