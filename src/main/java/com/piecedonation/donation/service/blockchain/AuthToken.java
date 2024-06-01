package com.piecedonation.donation.service.blockchain;

public record AuthToken(String authTokenId, String accountId, String iamUserId, String token, String expiryAt) {
}
