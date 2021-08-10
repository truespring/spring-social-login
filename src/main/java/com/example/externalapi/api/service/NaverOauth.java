package com.example.externalapi.api.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class NaverOauth implements SocialOauth {

    @Value("${sns.naver.url}")
    private String NAVER_SNS_BASE_URL;
    @Value("${sns.naver.client.id}")
    private String NAVER_SNS_CLIENT_ID;
    @Value("${sns.naver.client.secret}")
    private String NAVER_SNS_CLIENT_SECRET;
    @Value("${sns.naver.callback.url}")
    private String NAVER_SNS_CALLBACK_URL;
    @Value("${sns.naver.token.url}")
    private String NAVER_SNS_TOKEN_BASE_URL;

    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("client_id", NAVER_SNS_CLIENT_ID);
        params.put("redirect_uri", NAVER_SNS_CALLBACK_URL);
        params.put("response_type", "code");
        params.put("state", "state");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return NAVER_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "authorization_code");
        params.set("client_id", NAVER_SNS_CLIENT_ID);
        params.set("client_secret", NAVER_SNS_CLIENT_SECRET);
        params.set("code", code);
        params.set("state", "state");

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<JSONObject> responseEntity =
                restTemplate.postForEntity(NAVER_SNS_TOKEN_BASE_URL, restRequest, JSONObject.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(responseEntity.getBody()).toString();
        }
        return "네이버 로그인 요청 처리 실패";
    }
}