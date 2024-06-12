package com.piecedonation.donation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;

@Entity
@Table(name = "donation_history")
public class History {

    @Id
    @Column(name = "id", nullable = false)
    private String txHash;

    @Column(name = "owner_address", nullable = false)
    private String ownerAddress;

    @Column(name = "user_address", nullable = false)
    private String userAddress;

    @Column(name = "charity_address")
    private String charityAddress;

    @Column(name = "amount", nullable = false)
    private BigInteger amount;

    @Column(name = "timestamp", nullable = false)
    private String timestamp;

    protected History() {
    }

    public History(String txHash, String ownerAddress, String userAddress, String charityAddress, BigInteger amount, String timestamp) {
        this.txHash = txHash;
        this.ownerAddress = ownerAddress;
        this.userAddress = userAddress;
        this.charityAddress = charityAddress;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTxHash() {
        return txHash;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getCharityAddress() {
        return charityAddress;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
