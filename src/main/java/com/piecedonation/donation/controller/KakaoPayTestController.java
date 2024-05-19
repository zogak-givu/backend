package com.piecedonation.donation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KakaoPayTestController {

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/fail")
    public String fail(){
        return "fail";
    }
}
