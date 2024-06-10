package com.piecedonation.donation.domain;

import com.piecedonation.donation.domain.organization.Organization;
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
    private Organization organization;

    protected Wallet() {
    }

    public Wallet(String id, String address, Member member, Organization organization) {
        this.id = id;
        this.address = address;
        this.member = member;
        this.organization = organization;
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

    public Organization getOrganization() {
        return organization;
    }
}
