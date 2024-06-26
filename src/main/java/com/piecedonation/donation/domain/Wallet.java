package com.piecedonation.donation.domain;

import com.piecedonation.donation.domain.charity.Charity;
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

    @ManyToOne
    @JoinColumn(nullable = false)
    private Charity charity;

    protected Wallet() {
    }

    public Wallet(String id, String address, Member member, Charity charity) {
        this.id = id;
        this.address = address;
        this.member = member;
        this.charity = charity;
    }

    public boolean isMatch(Charity charity) {
        return this.charity.equals(charity);
    }
    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Member getMember() {
        return member;
    }

    public Charity getCharity() {
        return charity;
    }
}
