# 코드 구성 과정
Solidity 파일을 트러플을 이용하여 컴파일 하여
.abi파일, .bin파일을 만든 후 
이 두 파일을 web3j 커맨드도구로 이용해서 
main/java/com/piecedonation/donation/contract/SendTransaction_sol_SendTransaction을 만들었습니다.


# 해야할 것
1. web3j 의존성을 추가하였는데도 빌드가 에러나는 문제
2. API 사용 예시 보며 프로젝트에 적용할 방법 찾기


# Solidity 파일 원본 (SendTransaction.sol)
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.26;

contract SendTransaction {
    
    event TransactionSent(address indexed from, address indexed to, uint256 value);

    function sendTransaction(address payable to, uint256 value) public payable {
        require(msg.value == value, "Sent value must match the specified value");
        require(to != address(0), "Invalid address");

        // Transfer the funds
        to.transfer(value);

        // Emit the event
        emit TransactionSent(msg.sender, to, value);
    }
}

# API 엔드포인트 사용 예시
스마트 컨트랙트 배포:
POST http://localhost:8080/send-transaction/deploy

트랜잭션 전송:
POST http://localhost:8080/send-transaction/send
Body: {
    "contractAddress": "0xYourContractAddress",
    "toAddress": "0xRecipientAddress",
    "value": 10000  
}
