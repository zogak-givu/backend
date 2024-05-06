package com.piecedonation.donation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

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
