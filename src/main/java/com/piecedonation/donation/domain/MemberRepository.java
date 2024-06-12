package com.piecedonation.donation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

행import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String memberId);
}
