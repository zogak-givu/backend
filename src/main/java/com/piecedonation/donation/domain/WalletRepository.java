package com.piecedonation.donation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

    @Query(value = "SELECT * FROM wallet WHERE member_id = :memberId AND organization_id = :organizationId",
            nativeQuery = true)
    Optional<Wallet> findByMemberAndOrganization(@Param("memberId") String memberId, @Param("organizationId") String organizationId);

    @Query(value = "SELECT * FROM wallet WHERE member_id = :memberId",
            nativeQuery = true)
    List<Wallet> findByMember(@Param("memberId") String memberId);

}
