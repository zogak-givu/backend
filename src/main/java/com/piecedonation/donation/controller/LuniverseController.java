package com.piecedonation.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.piecedonation.donation.service.luniverse.LuniverseService;


@RestController
@RequestMapping("/api/luniverse")
public class LuniverseController {

    @Autowired
    private LuniverseService luniverseService;

    @GetMapping("/balance")
    public String getBalance() throws Exception {
        return luniverseService.getBalance();
    }

    @GetMapping("/nonce")
    public String getNonce() throws Exception {
        return luniverseService.getNonce();
    }

    @PostMapping("/deoa")
    public String createDEOA() throws Exception {
        return luniverseService.createDEOA();
    }

    @PostMapping("/transaction")
    public String executeTransaction() throws Exception {
        return luniverseService.executeTransaction();
    }

    @GetMapping("/transaction/history")
    public String getTransactionHistory() throws Exception {
        return luniverseService.getTransactionHistory();
    }
}
