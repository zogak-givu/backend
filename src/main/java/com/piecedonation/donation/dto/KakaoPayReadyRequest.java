package com.piecedonation.donation.dto;

import lombok.Data;

@Data
public class KakaoPayReadyRequest {
    String partner_order_id; //가맹점 주문번호, 최대 100자
    String partner_user_id; //가맹점 회원 id, 최대 100자
    String item_name;  //상품명, 최대 100자
    int quantity; //상품 수량
    int total_amount;//상품 총액
    int tax_free_amount; //상품 비과세 금액
    String approval_url; //결제 성공 시 redirect url, 최대 255자
    String cancel_url;//결제 취소 시 redirect url, 최대 255자
    String fail_url; //결제 실패 시 redirect url, 최대 255자
}
