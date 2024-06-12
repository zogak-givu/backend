package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.controller.HistoryResponse;
import com.piecedonation.donation.domain.History;
import com.piecedonation.donation.domain.HistoryRepository;
import com.piecedonation.donation.domain.Member;
import com.piecedonation.donation.domain.Wallet;
import com.piecedonation.donation.domain.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        List<HistoryResponse> historyResponses = histories.stream()
                .map(history -> HistoryResponse.from(history))
                .collect(Collectors.toList());

        // DateTimeFormatter 설정 (타임스탬프 형식에 맞게 변경)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 예시 형식
        // timestamp에 따라 최근순으로 정렬
        historyResponses.sort((hr1, hr2) -> {
            LocalDateTime dateTime1 = LocalDateTime.parse(hr1.timestamp(), formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse(hr2.timestamp(), formatter);
            return dateTime2.compareTo(dateTime1); // 최근순
        });

        return historyResponses;
    }
}
