package com.example.externalapi.api.service;

import com.example.externalapi.api.domain.google.GoogleOauthRequest;
import com.example.externalapi.api.domain.google.GoogleOauthResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GoogleOauth implements SocialOauth {

    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;
    @Value("${sns.google.user.url}")
    private String GOOGLE_SNS_USER_URL;

    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("scope", "email%20profile%20openid");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    /**
     * Spring Boot RestTemplate 을 활용한 방식
     *
     * @param code Api 에서 넘어온 code
     * @return body
     */
    @Override
    public String requestAccessToken(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        GoogleOauthRequest googleOAuthRequestParam = GoogleOauthRequest
                .builder()
                .clientId(GOOGLE_SNS_CLIENT_ID)
                .clientSecret(GOOGLE_SNS_CLIENT_SECRET)
                .code(code)
                .redirectUri(GOOGLE_SNS_CALLBACK_URL)
                .grantType("authorization_code").build();

        // JSON 파싱을 위한 기본값 세팅
        // 요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper 에 미리 설정해준다.
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, googleOAuthRequestParam, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            // Token Request
            GoogleOauthResponse result = mapper.readValue(responseEntity.getBody(), new TypeReference<>() {
            });

            // ID Token 만 추출 (사용자의 정보는 jwt 로 인코딩 되어있다)
            return result.getIdToken();
        }
        return "구글 로그인 요청 처리 실패";
    }

    @SneakyThrows
    @Override
    public String requestUserInfo(String accessTokenStr) {
        RestTemplate restTemplate = new RestTemplate();

        // url 을 만들어 id_token 에 인자를 담아서 던짐
        String requestUrl = UriComponentsBuilder.fromHttpUrl(GOOGLE_SNS_USER_URL)
                .queryParam("id_token", accessTokenStr).encode().toUriString();

        // return 받음
        String resultJson = restTemplate.getForObject(requestUrl, String.class);

        // ObjectMapper 로 타입변환을 할 수 있도록 함
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, String> userInfo = mapper.readValue(resultJson, new TypeReference<>() {
        });

        return userInfo.get("email");
    }
}
