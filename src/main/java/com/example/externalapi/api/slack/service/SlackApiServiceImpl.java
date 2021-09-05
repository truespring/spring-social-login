package com.example.externalapi.api.slack.service;

import com.example.externalapi.api.constants.SocialLoginType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SlackApiServiceImpl implements SlackApiService {

    @Value("${tool.slack.url}")
    private String SLACK_URL;
    @Value("${tool.slack.webhook}")
    private String SLACK_WEBHOOK;
    @Value("${tool.slack.token}")
    private String SLACK_TOKEN;
    @Value("${tool.slack.channel.id}")
    private String SLACK_CHANNEL_ID;

    @Override
    public void sendSlack(SocialLoginType socialLoginType, String email) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // slack 으로 보낼 메시지
        String text =
                "> 회원가입 성공!!🤘\n" +
                        "> Social Login Type : " + socialLoginType + "\n" +
                        "> Email : " + email;

        Map<String, Object> params = new HashMap<>();
        params.put("channel", SLACK_CHANNEL_ID);
        params.put("text", text);

        // slack url param 만들기
        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        // header setting
        headers.setBearerAuth(SLACK_TOKEN);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(param, headers);

        // slack url 만들기
        String urlStr = SLACK_URL + "?" + parameterString;

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(urlStr, restRequest, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error(">> Slack API Fail :: {}", responseEntity);
        }

    }

    @Override
    public void sendSlackBody(String text) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // header Content-type 에 application/json 넣는 법
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> restRequest = new HttpEntity<>(text, headers);

        restTemplate.postForEntity(SLACK_WEBHOOK, restRequest, String.class);
    }
}
