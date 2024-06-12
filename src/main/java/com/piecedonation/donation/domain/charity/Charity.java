package com.piecedonation.donation.domain.charity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;


@Entity
public class Charity {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String walletAddress;

    public Charity(String id, String name, String walletAddress) {
        this.id = id;
        this.name = name;
        this.walletAddress = walletAddress;
    }

    protected Charity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charity that = (Charity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
