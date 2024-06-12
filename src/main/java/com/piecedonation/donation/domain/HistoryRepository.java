package com.piecedonation.donation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, String> {
    List<History> findByUserAddress(String userAddress);
}
