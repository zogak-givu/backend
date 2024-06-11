package com.piecedonation.donation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3jConfig {
    @Value("${web3j.clientAddress}")
    private String luniverseRPCUrl;

    @Value("${web3j.privateKey}")
    private String privateKey;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(luniverseRPCUrl));
    }

    @Bean
    public ContractGasProvider contractGasProvider() {
        return new StaticGasProvider(BigInteger.ZERO, BigInteger.valueOf(4_300_000));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }
}
