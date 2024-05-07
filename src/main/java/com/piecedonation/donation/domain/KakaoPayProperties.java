package com.piecedonation.donation.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoPayProperties {
    @Value("${kakaopay.adminKey}")
    public static String adminKey;
    @Value("${kakaopay.cid}")
    public static String cid;
    @Value("${kakaopay.ready-url}")
    public static String readyUrl;
    @Value("${kakaopay.approve-url}")
    public static String approveUrl;
    @Value("${kakaopay.cancle-url}")
    public static String cancleUrl;
}