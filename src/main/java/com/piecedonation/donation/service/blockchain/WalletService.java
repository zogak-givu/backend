package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.Wallet;
import com.piecedonation.donation.domain.WalletRepository;
import com.piecedonation.donation.domain.charity.Charity;
import com.piecedonation.donation.domain.charity.CharityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WalletService {

    private final LuniverseClient luniverseClient;
    private final WalletRepository walletRepository;
    private final CharityRepository charityRepository;

    public WalletService(LuniverseClient luniverseClient, WalletRepository walletRepository, CharityRepository charityRepository) {
        this.luniverseClient = luniverseClient;
        this.walletRepository = walletRepository;
        this.charityRepository = charityRepository;
    }

    @Transactional
    public void createWallet(Member member, Charity organization) {
        WalletData walletData = luniverseClient.createAccount(member.getId());
        walletRepository.save(new Wallet(walletData.walletId(),walletData.address(), member, organization));
    }

    public void transferTokenToMemberWallet(Member member, String organizationName) {
        Charity organization = charityRepository.findByName(organizationName)
                .orElseThrow(() -> new IllegalArgumentException("토큰 전송 실패: 존재하지 않는 기부단체입니다."));
        Wallet wallet = walletRepository.findByMemberAndCharity(member.getId(), organization.getId())
                .orElseThrow(() -> new IllegalArgumentException("토큰 전송 실패: 존재하지 않는 사용자 지갑입니다."));

        luniverseClient.transferTokenToMemberWallet(wallet.getAddress());
    }
}
