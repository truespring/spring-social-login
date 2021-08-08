package com.example.externalapi.api.service;

import com.example.externalapi.api.constants.SocialLoginType;
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
    private final List<SocialOauth> socialOauthList; // implements 하는 클래스를 리스트로 만드는 것
    private final HttpServletResponse response;

    /**
     * 어떤 URL로 리다이렉트 할 것인지 판단
     *
     * @param socialLoginType 소셜 로그인 타입
     */
    public String request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        //        try {
//            response.sendRedirect(redirectURL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return socialOauth.getOauthRedirectURL();
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
     * type 메소드에서 클래스별 소셜을 찾아서 인자로 받은 클래스를 반환하기 위함
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
