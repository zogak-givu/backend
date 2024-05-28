package com.piecedonation.donation.controller;

import com.piecedonation.donation.service.blockchain.LuniverseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoPayTestController {

    private final LuniverseClient luniverseClient;

    public KakaoPayTestController(LuniverseClient luniverseClient) {
        this.luniverseClient = luniverseClient;
    }

    @GetMapping("/success")
    public String success(){
        luniverseClient.transferTokenToMemberWallet();
        return "success";
    }

    @GetMapping("/fail")
    public String fail(){
        return "fail";
    }
}
