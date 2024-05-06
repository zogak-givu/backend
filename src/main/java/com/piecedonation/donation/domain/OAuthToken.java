package com.piecedonation.donation.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piecedonation.donation.service.auth.MemberInfo;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class OAuthToken {

    private static final String JWT_TOKEN_DELIMITER = "\\.";
    private static final String PAYLOADS_DELIMITER = ",";
    private static final String ENTRY_DELIMITER = "\"";

    private static final int PAYLOAD_INDEX = 1;
    private static final int VALUE_INDEX = 3;

    private static final String SUBJECT = "sub";
    private static final String NAME = "name";

    private final String idToken;

    public OAuthToken(@JsonProperty("id_token") String idToken) {
        this.idToken = idToken;
    }

    public MemberInfo toMemberInfo() {
        final List<String> payloads = getPayloads();

        String openId = parse(payloads, SUBJECT);
        String memberName = parse(payloads, NAME);

        return new MemberInfo(openId, memberName);
    }

    private List<String> getPayloads() {
        final String payload = idToken.split(JWT_TOKEN_DELIMITER)[PAYLOAD_INDEX];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
        final String decoded = new String(decodedBytes);

        return Arrays.asList(decoded.split(PAYLOADS_DELIMITER));
    }

    private String parse(final List<String> payLoads, final String key) {
        final String entry = payLoads.stream()
                .filter(payload -> payload.contains(key))
                .findAny()
                .orElseThrow((() -> new IllegalArgumentException("토큰을 파싱할 수 없습니다. 해당하는 키가 존재하지 않습니다.")));

        return entry.split(ENTRY_DELIMITER)[VALUE_INDEX];
    }
}

