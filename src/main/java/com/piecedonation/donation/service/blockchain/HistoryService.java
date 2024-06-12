package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.controller.HistoryResponse;
import com.piecedonation.donation.domain.History;
import com.piecedonation.donation.domain.HistoryRepository;
import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.Wallet;
import com.piecedonation.donation.domain.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HistoryService {

    private final WalletRepository walletRepository;
    private final HistoryRepository historyRepository;

    public HistoryService(WalletRepository walletRepository, HistoryRepository historyRepository) {
        this.walletRepository = walletRepository;
        this.historyRepository = historyRepository;
    }

    public List<HistoryResponse> findByMember(Member member) {
        List<History> histories = new ArrayList<>();


        for (Wallet wallet :  member.getWallets()) {
            List<History> userCharityHistories = historyRepository.findByUserAddress(wallet.getAddress());
            histories.addAll(userCharityHistories);
        }

        return histories.stream()
                .map(history -> HistoryResponse.from(history))
                .collect(Collectors.toList());
    }
}
