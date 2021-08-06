package com.example.externalapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    @Value("${sns.kakao.url}")
    private String KAKAO_SNS_BASE_URL;
    @Value("${sns.kakao.client.id}")
    private String KAKAO_SNS_CLIENT_ID;
//    @Value("${sns.kakao.client.secret}")
//    private String KAKAO_SNS_CLIENT_SECRET;
    @Value("${sns.kakao.callback.url}")
    private String KAKAO_SNS_CALLBACK_URL;
    @Value("${sns.kakao.token.url}")
    private String KAKAO_SNS_TOKEN_BASE_URL;
    
    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_URL);
        params.put("response_type", "code");
        params.put("scope", "account_email");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return KAKAO_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }
}