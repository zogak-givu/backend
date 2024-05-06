package com.piecedonation.donation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Wallet {
    @Id
    private String id;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    protected Wallet() {
    }

    public Wallet(String id, String address, Member member) {
        this.id = id;
        this.address = address;
        this.member = member;
    }
}
