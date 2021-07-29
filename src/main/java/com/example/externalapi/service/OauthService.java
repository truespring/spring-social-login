package com.example.externalapi.service;

import com.example.externalapi.constants.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * serviceImpl 역할
 */
@Service
@RequiredArgsConstructor
public class OauthService {
    private final List<SocialOauth> socialOauthList;
    private final FacebookOauth facebookOauth;
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;
    private final NaverOauth naverOauth;
    private final HttpServletResponse response;

    /**
     * 어떤 URL로 리다이렉트 할 것인지 판단
     *
     * @param socialLoginType 소셜 로그인 타입
     */
    public void request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * accessToken 발급을 위한 요청
     *
     * @param socialLoginType 소셜 로그인 타입
     * @param code api의 코드
     * @return accessToken 요청
     */
    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.requestAccessToken(code);
    }

    /**
     * [GOOGLE, FACEBOOK, NAVER, KAKAO] 중 해당되는 소셜 로그인 타입인지 확인
     *
     * @param socialLoginType 인자로 받을 소셜 로그인 타입
     * @return 해당하는 소셜인지 아닌지
     */
    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
