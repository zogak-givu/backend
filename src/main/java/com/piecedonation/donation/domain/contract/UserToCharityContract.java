package com.piecedonation.donation.domain.contract;

import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public class UserToCharityContract extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b50604051610647380380610647833981810160405281019061003191906100d4565b805f806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506100ff565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100a38261007a565b9050919050565b6100b381610099565b81146100bd575f80fd5b50565b5f815190506100ce816100aa565b92915050565b5f602082840312156100e9576100e8610076565b5b5f6100f6848285016100c0565b91505092915050565b61053b8061010c5f395ff3fe608060405234801561000f575f80fd5b5060043610610034575f3560e01c8063bc3f6a5a14610038578063fc0c546a14610068575b5f80fd5b610052600480360381019061004d919061028d565b610086565b60405161005f919061030b565b60405180910390f35b6100706101d9565b60405161007d919061037f565b60405180910390f35b5f805f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd8787876040518463ffffffff1660e01b81526004016100e4939291906103b6565b6020604051808303815f875af1158015610100573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906101249190610415565b905080610166576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161015d906104c0565b60405180910390fd5b8473ffffffffffffffffffffffffffffffffffffffff168673ffffffffffffffffffffffffffffffffffffffff167f18729f1c66934e2edd48a56b06ff56624001b1805c122140f9ac3312320f9beb86866040516101c59291906104de565b60405180910390a380915050949350505050565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61022982610200565b9050919050565b6102398161021f565b8114610243575f80fd5b50565b5f8135905061025481610230565b92915050565b5f819050919050565b61026c8161025a565b8114610276575f80fd5b50565b5f8135905061028781610263565b92915050565b5f805f80608085870312156102a5576102a46101fc565b5b5f6102b287828801610246565b94505060206102c387828801610246565b93505060406102d487828801610279565b92505060606102e587828801610279565b91505092959194509250565b5f8115159050919050565b610305816102f1565b82525050565b5f60208201905061031e5f8301846102fc565b92915050565b5f819050919050565b5f61034761034261033d84610200565b610324565b610200565b9050919050565b5f6103588261032d565b9050919050565b5f6103698261034e565b9050919050565b6103798161035f565b82525050565b5f6020820190506103925f830184610370565b92915050565b6103a18161021f565b82525050565b6103b08161025a565b82525050565b5f6060820190506103c95f830186610398565b6103d66020830185610398565b6103e360408301846103a7565b949350505050565b6103f4816102f1565b81146103fe575f80fd5b50565b5f8151905061040f816103eb565b92915050565b5f6020828403121561042a576104296101fc565b5b5f61043784828501610401565b91505092915050565b5f82825260208201905092915050565b7f5472616e736665722066726f6d207573657220746f20636861726974792066615f8201527f696c656400000000000000000000000000000000000000000000000000000000602082015250565b5f6104aa602483610440565b91506104b582610450565b604082019050919050565b5f6020820190508181035f8301526104d78161049e565b9050919050565b5f6040820190506104f15f8301856103a7565b6104fe60208301846103a7565b939250505056fea26469706673582212205caa011782ee5ce247ce89ac543f24dd379bd222e84354962df2eb85d7772eaf64736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_TOKEN = "token";

    public static final String FUNC_TRANSFERFROMUSERTOCHARITY = "transferFromUserToCharity";

    public static final Event DONATIONCOMPLETED_EVENT = new Event("DonationCompleted",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;

    @Deprecated
    protected UserToCharityContract(String contractAddress, Web3j web3j, Credentials credentials,
                                    BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UserToCharityContract(String contractAddress, Web3j web3j, Credentials credentials,
                                    ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UserToCharityContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                                    BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UserToCharityContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                                    ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DonationCompletedEventResponse> getDonationCompletedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DONATIONCOMPLETED_EVENT, transactionReceipt);
        ArrayList<DonationCompletedEventResponse> responses = new ArrayList<DonationCompletedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DonationCompletedEventResponse typedResponse = new DonationCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.charity = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DonationCompletedEventResponse getDonationCompletedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DONATIONCOMPLETED_EVENT, log);
        DonationCompletedEventResponse typedResponse = new DonationCompletedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.charity = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DonationCompletedEventResponse> donationCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDonationCompletedEventFromLog(log));
    }

    public Flowable<DonationCompletedEventResponse> donationCompletedEventFlowable(DefaultBlockParameter startBlock,
                                                                                   DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DONATIONCOMPLETED_EVENT));
        return donationCompletedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> token() {
        final Function function = new Function(FUNC_TOKEN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFromUserToCharity(String user, String charity, BigInteger amount,
                                                                            BigInteger id) {
        final Function function = new Function(
                FUNC_TRANSFERFROMUSERTOCHARITY,
                Arrays.<Type>asList(new Address(160, user),
                        new Address(160, charity),
                        new Uint256(amount),
                        new Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static UserToCharityContract load(String contractAddress, Web3j web3j, Credentials credentials,
                                             BigInteger gasPrice, BigInteger gasLimit) {
        return new UserToCharityContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UserToCharityContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                                             BigInteger gasPrice, BigInteger gasLimit) {
        return new UserToCharityContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UserToCharityContract load(String contractAddress, Web3j web3j, Credentials credentials,
                                             ContractGasProvider contractGasProvider) {
        return new UserToCharityContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UserToCharityContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
                                             ContractGasProvider contractGasProvider) {
        return new UserToCharityContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<UserToCharityContract> deploy(Web3j web3j, Credentials credentials,
                                                           ContractGasProvider contractGasProvider, String tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress)));
        return deployRemoteCall(UserToCharityContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<UserToCharityContract> deploy(Web3j web3j, TransactionManager transactionManager,
                                                           ContractGasProvider contractGasProvider, String tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress)));
        return deployRemoteCall(UserToCharityContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<UserToCharityContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
                                                           BigInteger gasLimit, String tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress)));
        return deployRemoteCall(UserToCharityContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<UserToCharityContract> deploy(Web3j web3j, TransactionManager transactionManager,
                                                           BigInteger gasPrice, BigInteger gasLimit, String tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress)));
        return deployRemoteCall(UserToCharityContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class DonationCompletedEventResponse extends BaseEventResponse {
        public String user;

        public String charity;

        public BigInteger amount;

        public BigInteger id;
    }
}
