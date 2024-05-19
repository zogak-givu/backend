package com.piecedonation.donation.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class KakaoPayProperties {
    @Value("${kakaopay.secret-key}")
    public  String secretKey;
    @Value("${kakaopay.cid}")
    public  String cid;
    @Value("${kakaopay.ready-url}")
    public  String readyUrl;
    @Value("${kakaopay.approve-url}")
    public  String approveUrl;
    @Value("${kakaopay.cancle-url}")
    public  String cancleUrl;

}