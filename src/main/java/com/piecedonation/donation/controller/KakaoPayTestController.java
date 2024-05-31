package com.piecedonation.donation.controller;

import com.piecedonation.donation.domain.MemberRepository;
import com.piecedonation.donation.service.blockchain.LuniverseClient;
import com.piecedonation.donation.service.blockchain.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoPayTestController {

    LuniverseClient luniverseClient;

    WalletService walletService;

    MemberRepository memberRepository;

    public KakaoPayTestController(LuniverseClient luniverseClient, WalletService walletService, MemberRepository memberRepository) {
        this.luniverseClient = luniverseClient;
        this.walletService = walletService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping("/transfer")
    public String tranfer(@RequestParam("to") String to) {
        luniverseClient.transferTokenToMemberWallet(to);
        return "transfer success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }
}
