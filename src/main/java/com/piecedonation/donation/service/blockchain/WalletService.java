package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.Wallet;
import com.piecedonation.donation.domain.WalletRepository;
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

    public void createWallet(Member member) {
        WalletData walletData = luniverseClient.createAccount(member.getId());
        walletRepository.save(new Wallet(walletData.walletId(),walletData.address(), member));
    }

    public String findHistory(String accountId) {
        return luniverseClient.findHistory(accountId);
    }

    public String findTxHistory(String txId) {
        try {
            return luniverseClient.getTxHistory(txId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
