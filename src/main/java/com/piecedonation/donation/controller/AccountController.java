package com.piecedonation.donation.controller;

import com.piecedonation.donation.service.blockchain.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final WalletService walletService;

    public AccountController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<String> login(@PathVariable("accountId") String accountId) {
        String response = walletService.findHistory(accountId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/history")
    public ResponseEntity<String> postTxHistory() {

        return ResponseEntity.ok("response");
    }

    @GetMapping("/tx/{txId}")
    public ResponseEntity<String> getTx(@PathVariable("txId") String txId) {
        String response = walletService.findTxHistory(txId);
        return ResponseEntity.ok(response);
    }
}
