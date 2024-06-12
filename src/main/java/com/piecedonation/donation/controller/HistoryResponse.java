package com.piecedonation.donation.controller;

import com.piecedonation.donation.domain.History;

public record HistoryResponse(String txHash, String from, String to, int amount, String timestamp, String charityName) {

    public static HistoryResponse from(History history) {
        return new HistoryResponse(history.getTxHash(), history.getUserAddress(), history.getCharityAddress(),
                history.getAmount().intValue()*100, history.getTimestamp(), history.getCharityName());
    }
}
