package com.piecedonation.donation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
public class Member {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = {PERSIST, MERGE})
    private List<Wallet> wallets = new ArrayList<>();

    protected Member() {
    }

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
