package com.piecedonation.donation.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class SendTransaction_sol_SendTransaction extends Contract {
    public static final String BINARY = "6080604052348015600e575f80fd5b506103cd8061001c5f395ff3fe60806040526004361061001d575f3560e01c8063d709deb714610021575b5f80fd5b61003b6004803603810190610036919061022b565b61003d565b005b80341461007f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610076906102e9565b60405180910390fd5b5f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036100ed576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016100e490610351565b60405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f19350505050158015610130573d5f803e3d5ffd5b508173ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f09305666a7e2df9637be4c1799541cc5616f337b7433060312e6b79d549d8fd88360405161018e919061037e565b60405180910390a35050565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6101c78261019e565b9050919050565b6101d7816101bd565b81146101e1575f80fd5b50565b5f813590506101f2816101ce565b92915050565b5f819050919050565b61020a816101f8565b8114610214575f80fd5b50565b5f8135905061022581610201565b92915050565b5f80604083850312156102415761024061019a565b5b5f61024e858286016101e4565b925050602061025f85828601610217565b9150509250929050565b5f82825260208201905092915050565b7f53656e742076616c7565206d757374206d6174636820746865207370656369665f8201527f6965642076616c75650000000000000000000000000000000000000000000000602082015250565b5f6102d3602983610269565b91506102de82610279565b604082019050919050565b5f6020820190508181035f830152610300816102c7565b9050919050565b7f496e76616c6964206164647265737300000000000000000000000000000000005f82015250565b5f61033b600f83610269565b915061034682610307565b602082019050919050565b5f6020820190508181035f8301526103688161032f565b9050919050565b610378816101f8565b82525050565b5f6020820190506103915f83018461036f565b9291505056fea26469706673582212200b14a0a959c515a512a2fb2200130906b9349b1fecb3574286d451de1985f2c164736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_SENDTRANSACTION = "sendTransaction";

    public static final Event TRANSACTIONSENT_EVENT = new Event("TransactionSent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SendTransaction_sol_SendTransaction(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SendTransaction_sol_SendTransaction(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SendTransaction_sol_SendTransaction(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SendTransaction_sol_SendTransaction(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<TransactionSentEventResponse> getTransactionSentEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSACTIONSENT_EVENT, transactionReceipt);
        ArrayList<TransactionSentEventResponse> responses = new ArrayList<TransactionSentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransactionSentEventResponse typedResponse = new TransactionSentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransactionSentEventResponse getTransactionSentEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSACTIONSENT_EVENT, log);
        TransactionSentEventResponse typedResponse = new TransactionSentEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransactionSentEventResponse> transactionSentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransactionSentEventFromLog(log));
    }

    public Flowable<TransactionSentEventResponse> transactionSentEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSACTIONSENT_EVENT));
        return transactionSentEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> sendTransaction(String to, BigInteger value,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_SENDTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    @Deprecated
    public static SendTransaction_sol_SendTransaction load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SendTransaction_sol_SendTransaction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SendTransaction_sol_SendTransaction load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SendTransaction_sol_SendTransaction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SendTransaction_sol_SendTransaction load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SendTransaction_sol_SendTransaction(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SendTransaction_sol_SendTransaction load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SendTransaction_sol_SendTransaction(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SendTransaction_sol_SendTransaction> deploy(Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SendTransaction_sol_SendTransaction.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<SendTransaction_sol_SendTransaction> deploy(Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SendTransaction_sol_SendTransaction.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<SendTransaction_sol_SendTransaction> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SendTransaction_sol_SendTransaction.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<SendTransaction_sol_SendTransaction> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SendTransaction_sol_SendTransaction.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class TransactionSentEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }
}