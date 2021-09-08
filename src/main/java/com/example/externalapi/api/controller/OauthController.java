package com.example.externalapi.api.controller;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.api.service.OauthService;
import com.example.externalapi.api.slack.service.SlackApiServiceImpl;
import com.example.externalapi.app.common.dto.CallbackDto;
import com.example.externalapi.app.common.service.CallBackServiceImpl;
import com.example.externalapi.app.user.domain.entity.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final Map<String, Object> returnMap = new HashMap<>();
    private final SlackApiServiceImpl slackApiService;
    private final CallBackServiceImpl callBackService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     */
    @GetMapping("/{socialLoginType}")
    public String socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        return oauthService.request(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     */
    @GetMapping(value = "/{socialLoginType}/callback")
    public CallbackDto callback(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) throws JsonProcessingException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        String accessTokenStr = oauthService.requestAccessToken(socialLoginType, code);
        Users userInfo = oauthService.requestUserInfo(socialLoginType, accessTokenStr);
//        returnMap.put("accessToken", accessTokenStr);
//        returnMap.put("socialLoginType", socialLoginType);
//        returnMap.put("userInfo", userInfo);
//        return "success";
        return callBackService.callBackInfo(accessTokenStr, socialLoginType, userInfo);
    }

    @GetMapping(value = "/success")
    public @ResponseBody
    Map<String, Object> callbackPage() {
        log.info(">> 로그인 성공페이지 요청 받음 :: {}", returnMap);
        return returnMap;
    }

    @GetMapping(value = "/slack/api")
    public void sendSlackApi(@RequestParam(name = "text") String text) {
        slackApiService.sendSlackBody(text);
    }
}