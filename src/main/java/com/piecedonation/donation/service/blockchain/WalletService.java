package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.Wallet;
import com.piecedonation.donation.domain.WalletRepository;
import com.piecedonation.donation.domain.organization.Organization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WalletService {

    private final LuniverseClient luniverseClient;
    private final WalletRepository walletRepository;

    public WalletService(LuniverseClient luniverseClient, WalletRepository walletRepository) {
        this.luniverseClient = luniverseClient;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void createWallet(Member member, Organization organization) {
        WalletData walletData = luniverseClient.createAccount(member.getId());
        walletRepository.save(new Wallet(walletData.walletId(),walletData.address(), member, organization));
    }
}
