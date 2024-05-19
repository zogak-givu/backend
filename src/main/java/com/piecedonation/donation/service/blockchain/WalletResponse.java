package com.piecedonation.donation.service.blockchain;

public record WalletResponse(boolean result, int status, WalletData data, String code) {
}
