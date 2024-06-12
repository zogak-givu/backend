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
public class OwnerToUserContract extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b506040516108b63803806108b683398181016040528101906100319190610115565b815f806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508060015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050610153565b5f80fd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100e4826100bb565b9050919050565b6100f4816100da565b81146100fe575f80fd5b50565b5f8151905061010f816100eb565b92915050565b5f806040838503121561012b5761012a6100b7565b5b5f61013885828601610101565b925050602061014985828601610101565b9150509250929050565b610756806101605f395ff3fe608060405234801561000f575f80fd5b506004361061003f575f3560e01c806339836024146100435780638c1f17c214610061578063fc0c546a1461007d575b5f80fd5b61004b61009b565b604051610058919061039c565b60405180910390f35b61007b60048036038101906100769190610427565b6100c0565b005b6100856102ff565b60405161009291906104be565b60405180910390f35b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd8686856040518463ffffffff1660e01b815260040161011c939291906104f5565b6020604051808303815f875af1158015610138573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061015c919061055f565b61019b576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101929061060a565b60405180910390fd5b8273ffffffffffffffffffffffffffffffffffffffff168473ffffffffffffffffffffffffffffffffffffffff168673ffffffffffffffffffffffffffffffffffffffff167f81f4e24a18477e8fafa59fb7f5e4ebd4bdc3de1563b4e9ae0da2113b901235758585604051610211929190610628565b60405180910390a460015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663bc3f6a5a858585856040518563ffffffff1660e01b8152600401610279949392919061064f565b6020604051808303815f875af1158015610295573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102b9919061055f565b6102f8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102ef90610702565b60405180910390fd5b5050505050565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f819050919050565b5f61036461035f61035a84610322565b610341565b610322565b9050919050565b5f6103758261034a565b9050919050565b5f6103868261036b565b9050919050565b6103968161037c565b82525050565b5f6020820190506103af5f83018461038d565b92915050565b5f80fd5b5f6103c382610322565b9050919050565b6103d3816103b9565b81146103dd575f80fd5b50565b5f813590506103ee816103ca565b92915050565b5f819050919050565b610406816103f4565b8114610410575f80fd5b50565b5f81359050610421816103fd565b92915050565b5f805f805f60a086880312156104405761043f6103b5565b5b5f61044d888289016103e0565b955050602061045e888289016103e0565b945050604061046f888289016103e0565b935050606061048088828901610413565b925050608061049188828901610413565b9150509295509295909350565b5f6104a88261036b565b9050919050565b6104b88161049e565b82525050565b5f6020820190506104d15f8301846104af565b92915050565b6104e0816103b9565b82525050565b6104ef816103f4565b82525050565b5f6060820190506105085f8301866104d7565b61051560208301856104d7565b61052260408301846104e6565b949350505050565b5f8115159050919050565b61053e8161052a565b8114610548575f80fd5b50565b5f8151905061055981610535565b92915050565b5f60208284031215610574576105736103b5565b5b5f6105818482850161054b565b91505092915050565b5f82825260208201905092915050565b7f5472616e736665722066726f6d206f776e657220746f2075736572206661696c5f8201527f6564000000000000000000000000000000000000000000000000000000000000602082015250565b5f6105f460228361058a565b91506105ff8261059a565b604082019050919050565b5f6020820190508181035f830152610621816105e8565b9050919050565b5f60408201905061063b5f8301856104e6565b61064860208301846104e6565b9392505050565b5f6080820190506106625f8301876104d7565b61066f60208301866104d7565b61067c60408301856104e6565b61068960608301846104e6565b95945050505050565b7f5472616e736665722066726f6d207573657220746f20636861726974792066615f8201527f696c656400000000000000000000000000000000000000000000000000000000602082015250565b5f6106ec60248361058a565b91506106f782610692565b604082019050919050565b5f6020820190508181035f830152610719816106e0565b905091905056fea264697066735822122040663d75da97bfb2db2aa9bfaedaf067b50bb911e73955016562d435369fa2a764736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_DONATE = "donate";

    public static final String FUNC_TOKEN = "token";

    public static final String FUNC_USERTOCHARITYCONTRACT = "userToCharityContract";

    public static final Event DONATIONINITIATED_EVENT = new Event("DonationInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected OwnerToUserContract(String contractAddress, Web3j web3j, Credentials credentials,
                                  BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OwnerToUserContract(String contractAddress, Web3j web3j, Credentials credentials,
                                  ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OwnerToUserContract(String contractAddress, Web3j web3j,
                                  TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OwnerToUserContract(String contractAddress, Web3j web3j,
                                  TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DonationInitiatedEventResponse> getDonationInitiatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Log> logs = transactionReceipt.getLogs();
        List<DonationInitiatedEventResponse> responses = new ArrayList<>(logs.size());
        for (Log log : logs) {
            EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DONATIONINITIATED_EVENT, log);
            DonationInitiatedEventResponse typedResponse = new DonationInitiatedEventResponse();
            typedResponse.log = log;
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.charity = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DonationInitiatedEventResponse getDonationInitiatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DONATIONINITIATED_EVENT, log);
        DonationInitiatedEventResponse typedResponse = new DonationInitiatedEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.charity = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DonationInitiatedEventResponse> donationInitiatedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDonationInitiatedEventFromLog(log));
    }

    public Flowable<DonationInitiatedEventResponse> donationInitiatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DONATIONINITIATED_EVENT));
        return donationInitiatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> donate(String owner, String user, String charity,
            BigInteger amount, BigInteger id) {
        final Function function = new Function(
                FUNC_DONATE, 
                Arrays.<Type>asList(new Address(160, owner),
                new Address(160, user),
                new Address(160, charity),
                new Uint256(amount),
                new Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> token() {
        final Function function = new Function(FUNC_TOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> userToCharityContract() {
        final Function function = new Function(FUNC_USERTOCHARITYCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static OwnerToUserContract load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnerToUserContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OwnerToUserContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnerToUserContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OwnerToUserContract load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OwnerToUserContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OwnerToUserContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OwnerToUserContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OwnerToUserContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String tokenAddress,
            String userToCharityContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress),
                new Address(160, userToCharityContractAddress)));
        return deployRemoteCall(OwnerToUserContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<OwnerToUserContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String tokenAddress, String userToCharityContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress),
                new Address(160, userToCharityContractAddress)));
        return deployRemoteCall(OwnerToUserContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OwnerToUserContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String tokenAddress,
            String userToCharityContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress),
                new Address(160, userToCharityContractAddress)));
        return deployRemoteCall(OwnerToUserContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OwnerToUserContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String tokenAddress, String userToCharityContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, tokenAddress),
                new Address(160, userToCharityContractAddress)));
        return deployRemoteCall(OwnerToUserContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class DonationInitiatedEventResponse extends BaseEventResponse {
        public String owner;

        public String user;

        public String charity;

        public BigInteger amount;

        public BigInteger id;
    }
}
