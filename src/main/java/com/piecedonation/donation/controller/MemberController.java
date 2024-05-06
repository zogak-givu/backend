package com.piecedonation.donation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/members")
public class MemberController {

    @GetMapping("/{}")
    public ResponseEntity<String> login() {

        return ResponseEntity.status(200).build();
    }


}
