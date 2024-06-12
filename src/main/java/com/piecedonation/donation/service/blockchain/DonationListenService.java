package com.piecedonation.donation.service.blockchain;

import com.piecedonation.donation.domain.History;
import com.piecedonation.donation.domain.HistoryRepository;
import com.piecedonation.donation.domain.contract.OwnerToUserContract;
import com.piecedonation.donation.domain.contract.UserToCharityContract;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class DonationListenService {

    private final Web3j web3j;
    private final ContractGasProvider gasProvider;
    private final Credentials credentials;
    private final HistoryRepository historyRepository;

    public DonationListenService(Web3j web3j, ContractGasProvider gasProvider, Credentials credentials, HistoryRepository historyRepository) {
        this.web3j = web3j;
        this.gasProvider = gasProvider;
        this.credentials = credentials;
        this.historyRepository = historyRepository;
    }

    private static final String OWNER_TO_USER_CONTRACT_ADDRESS = LuniverseClient.OWNER_USER_CONTRACT_ADDRESS;
    private static final String USER_TO_CHARITY_CONTRACT_ADDRESS = LuniverseClient.USER_CHARITY_CONTRACT_ADDRESS;

    @PostConstruct
    public void init() {
        OwnerToUserContract ownerToUserContract = OwnerToUserContract.load(OWNER_TO_USER_CONTRACT_ADDRESS, web3j, credentials, gasProvider);
        UserToCharityContract userToCharityContract = UserToCharityContract.load(USER_TO_CHARITY_CONTRACT_ADDRESS, web3j, credentials, gasProvider);

        LocalDateTime nowInKorea = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        //이벤트 구독
        ownerToUserContract.donationInitiatedEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
                .subscribe(event -> {
                    String owner = event.owner;
                    String user = event.user;
                    String charity = event.charity;
                    BigInteger amount = event.amount;
                    BigInteger id = event.id;
                    String TxHash = event.log.getTransactionHash();
                    String timestamp = nowInKorea.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    System.out.println(String.format("DonationInitiated: %s - Owner: %s, User: %s, Charity: %s, Amount: %s, ID: %s, Time: %s",
                            TxHash, owner, user, charity, amount, id, timestamp));

                    historyRepository.save(new History(TxHash, owner, user, charity, amount, timestamp));

                }, throwable -> {
                    System.err.println("Error in donationInitiatedEventFlowable: " + throwable.getMessage());
                    throwable.printStackTrace();
                });
    }
}
