package com.example.externalapi.api.service;

import com.example.externalapi.api.domain.github.GithubOauthResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
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
    @Value("${sns.github.user.url}")
    private String GITHUB_SNS_USER_URL;

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
        var restTemplate = new RestTemplate();
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

        headers.set("Authorization", "token " + accessToken);
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(GITHUB_SNS_USER_URL, HttpMethod.GET, restRequest, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) return "깃허브 로그인 요청 처리 실패";

        List<GithubOauthResponse> list = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
        });

        return list.stream().filter(GithubOauthResponse::getPrimary).map(GithubOauthResponse::getEmail).findFirst()
                .orElse("");
    }
}