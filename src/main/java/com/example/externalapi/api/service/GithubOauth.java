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
public class GithubOauth implements SocialOauth {

    @Value("${sns.github.url}")
    private String GITHUB_SNS_BASE_URL;
    @Value("${sns.github.client.id}")
    private String GITHUB_SNS_CLIENT_ID;
    @Value("${sns.github.client.secret}")
    private String GITHUB_SNS_CLIENT_SECRET;
    @Value("${sns.github.callback.url}")
    private String GITHUB_SNS_CALLBACK_URL;
    @Value("${sns.github.token.url}")
    private String GITHUB_SNS_TOKEN_BASE_URL;

    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("client_id", GITHUB_SNS_CLIENT_ID);
        params.put("scope", "user:email");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GITHUB_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("code", code);
        params.set("client_id", GITHUB_SNS_CLIENT_ID);
        params.set("client_secret", GITHUB_SNS_CLIENT_SECRET);
        params.set("redirect_uri", GITHUB_SNS_CALLBACK_URL);
        params.set("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<JSONObject> responseEntity =
                restTemplate.postForEntity(GITHUB_SNS_TOKEN_BASE_URL, restRequest, JSONObject.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(responseEntity.getBody()).toString();
        }
        return "깃허브 로그인 요청 처리 실패";
    }
}