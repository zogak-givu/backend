package com.piecedonation.donation.service.smartcontract;

import com.piecedonation.donation.contract.SendTransaction_sol_SendTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

@Service
public class SendTransactionService {

    private final Web3j web3j;
    private final Credentials credentials;

    public SendTransactionService(@Value("${web3j.client-address}") String clientAddress,
                                  @Value("${credentials.private-key}") String privateKey) {
        this.web3j = Web3j.build(new HttpService(clientAddress));
        this.credentials = Credentials.create(privateKey);
    }

    public String deployContract() throws Exception {
        SendTransaction contract = SendTransaction.deploy(web3j, credentials, new DefaultGasProvider()).send();
        return contract.getContractAddress();
    }

    public TransactionReceipt sendTransaction(String contractAddress, String toAddress, BigInteger value) throws Exception {
        SendTransaction contract = SendTransaction.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return contract.sendTransaction(new org.web3j.abi.datatypes.Address(toAddress), value).send();
    }
}
