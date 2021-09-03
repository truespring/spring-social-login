package com.example.externalapi.api.service;

import com.example.externalapi.api.domain.github.GithubOauthResponse;
import com.example.externalapi.api.domain.kakao.KakaoOauthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    @Value("${sns.kakao.url}")
    private String KAKAO_SNS_BASE_URL;
    @Value("${sns.kakao.client.id}")
    private String KAKAO_SNS_CLIENT_ID;
    @Value("${sns.kakao.client.secret}")
    private String KAKAO_SNS_CLIENT_SECRET;
    @Value("${sns.kakao.callback.url}")
    private String KAKAO_SNS_CALLBACK_URL;
    @Value("${sns.kakao.token.url}")
    private String KAKAO_SNS_TOKEN_BASE_URL;
    @Value("${sns.kakao.user.url}")
    private String KAKAO_SNS_USER_URL;

    @Override
    public String getOauthRedirectURL() {

        // 카카오 요청 파라미터
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
        var restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // 토큰 발급을 위한 요청 파라미터
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("code", code);
        params.set("client_id", KAKAO_SNS_CLIENT_ID);
        params.set("client_secret", KAKAO_SNS_CLIENT_SECRET);
        params.set("redirect_uri", KAKAO_SNS_CALLBACK_URL);
        params.set("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<JSONObject> responseEntity =
                restTemplate.postForEntity(KAKAO_SNS_TOKEN_BASE_URL, restRequest, JSONObject.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(responseEntity.getBody()).toString();
        }
        return "카카오 로그인 요청 처리 실패";
    }

    @Override
    public String requestUserInfo(String accessTokenStr) throws JsonProcessingException {

        String accessToken = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JSONObject dataJson = mapper.readValue(new JSONParser().parse(accessTokenStr).toString(), JSONObject.class);
            accessToken = dataJson.get("access_token").toString();
        } catch (ParseException | ClassCastException | NullPointerException e) {
            e.printStackTrace();
        }

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        headers.set("Authorization", "Bearer " + accessToken); // header 에 값 담는 방법
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(KAKAO_SNS_USER_URL, restRequest, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return "카카오 로그인 요청 처리 실패";
        }

        KakaoOauthResponse response = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        return response.getKakao_account().getEmail();
    }
}