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
public class ContractService {
    private final LuniverseClient luniverseClient;
    private final CharityRepository charityRepository;
    private final WalletRepository walletRepository;

    public ContractService(LuniverseClient luniverseClient, CharityRepository charityRepository, WalletRepository walletRepository) {
        this.luniverseClient = luniverseClient;
        this.charityRepository = charityRepository;
        this.walletRepository = walletRepository;
    }

    public void executeContract(Member member, String organizationName, String amount) {
        Charity organization = charityRepository.findByName(organizationName)
                .orElseThrow(() -> new IllegalArgumentException("계약 실행 실패:해당 단체를 찾을 수 없습니다."));

        Wallet memberWallet = walletRepository.findByMemberAndOrganization(member.getId(), organization.getId())
                .orElseThrow(() -> new IllegalArgumentException("계약 실행 실패:해당하는 회원의 단체지갑을 찾을 수 없습니다."));

        luniverseClient.executeContract(memberWallet.getAddress(), organization.getWalletAddress(), amount);
    }
}
