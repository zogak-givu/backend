package com.piecedonation.donation.domain.charity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharityRepository extends JpaRepository<Charity, String> {
    Optional<Charity> findByName(String name);
}
