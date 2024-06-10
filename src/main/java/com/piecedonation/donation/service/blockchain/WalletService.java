package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.Wallet;
import com.piecedonation.donation.domain.WalletRepository;
import com.piecedonation.donation.domain.organization.Organization;
import com.piecedonation.donation.domain.organization.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WalletService {

    private final LuniverseClient luniverseClient;
    private final WalletRepository walletRepository;
    private final OrganizationRepository organizationRepository;

    public WalletService(LuniverseClient luniverseClient, WalletRepository walletRepository, OrganizationRepository organizationRepository) {
        this.luniverseClient = luniverseClient;
        this.walletRepository = walletRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public void createWallet(Member member, Organization organization) {
        WalletData walletData = luniverseClient.createAccount(member.getId());
        walletRepository.save(new Wallet(walletData.walletId(),walletData.address(), member, organization));
    }

    public void transferTokenToMemberWallet(Member member, String organizationName) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new IllegalArgumentException("토큰 전송 실패: 존재하지 않는 기부단체입니다."));
        Wallet wallet = walletRepository.findByMemberAndOrganization(member.getId(), organization.getId())
                .orElseThrow(() -> new IllegalArgumentException("토큰 전송 실패: 존재하지 않는 사용자 지갑입니다."));

        luniverseClient.transferTokenToMemberWallet(wallet.getAddress());
    }
}
