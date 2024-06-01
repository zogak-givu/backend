package com.piecedonation.donation.dto;

import lombok.Data;

@Data
public class KaKaoPayCancleRequest {
    String cid;	//가맹점 코드, 10자
    String tid; //결제 고유번호, 20자
    int cancel_amount; //취소 금액
    int cancel_tax_free_amount; //취소 비과세 금액
}
