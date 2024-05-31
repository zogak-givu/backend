package com.piecedonation.donation.domain.organization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Organization {
    @Id
    private String id;

    @Column
    private String name;

    public Organization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Organization() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
