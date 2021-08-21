package com.example.externalapi.api.controller;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.api.service.OauthService;
import com.example.externalapi.app.user.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final Map<String, Object> returnMap = new HashMap<>();

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     *
     * @param socialLoginType (GOOGLE, GITHUB, NAVER, KAKAO)
     * @return socialLoginPage
     */
    @GetMapping(value = "/{socialLoginType}")
    public RedirectView socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        RedirectView redirectView = new RedirectView();
        String url = oauthService.request(socialLoginType);
        redirectView.setUrl(url);
        return redirectView;
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     *
     * @param socialLoginType (GOOGLE, GITHUB, NAVER, KAKAO)
     * @param code            API Server 로부터 넘어오는 code
     * @return 로그인 완료 후 화면(임시)
     */
    @GetMapping(value = "/{socialLoginType}/callback")
    public String callback(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        String accessTokenStr = oauthService.requestAccessToken(socialLoginType, code);
        Users userInfo = oauthService.requestUserInfo(socialLoginType, accessTokenStr);
        returnMap.put("accessToken", accessTokenStr);
        returnMap.put("socialLoginType", socialLoginType);
        returnMap.put("userInfo", userInfo);
        return "success";
    }

    @GetMapping(value = "/success")
    public @ResponseBody
    Map<String, Object> callbackPage() {
        log.info(">> 로그인 성공페이지 요청 받음 :: {}", returnMap);
        return returnMap;
    }
}