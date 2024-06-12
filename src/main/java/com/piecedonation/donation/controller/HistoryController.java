package com.piecedonation.donation.controller;

import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.service.blockchain.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<List<HistoryResponse>> memberHistory(Member member) {
        List<HistoryResponse> histories = historyService.findByMember(member);
        return ResponseEntity.ok(histories);
    }
}
