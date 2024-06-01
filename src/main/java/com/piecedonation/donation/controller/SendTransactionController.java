package com.piecedonation.donation.controller;

import com.piecedonation.donation.service.smartcontract.SendTransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/send-transaction")
public class SendTransactionController {

    private final SendTransactionService sendTransactionService;

    public SendTransactionController(SendTransactionService sendTransactionService) {
        this.sendTransactionService = sendTransactionService;
    }

    @PostMapping("/deploy")
    public String deployContract() throws Exception {
        return sendTransactionService.deployContract();
    }

    @PostMapping("/send")
    public String sendTransaction(
            @RequestParam String contractAddress, 
            @RequestParam String toAddress, 
            @RequestParam BigInteger value) throws Exception {
        sendTransactionService.sendTransaction(contractAddress, toAddress, value);
        return "Transaction sent successfully";
    }
}