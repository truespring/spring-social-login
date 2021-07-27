package com.example.externalapi.service;

import com.example.externalapi.constants.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final FacebookOauth facebookOauth;
    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;
    private final NaverOauth naverOauth;
    private final HttpServletResponse response;

    public void request(SocialLoginType socialLoginType) {
        String redirectURL;
        switch (socialLoginType) {
            case GOOGLE: {
                redirectURL = googleOauth.getOauthRedirectURL();
            } break;
            case FACEBOOK: {
                redirectURL = facebookOauth.getOauthRedirectURL();
            } break;
            case KAKAO: {
                redirectURL = kakaoOauth.getOauthRedirectURL();
            } break;
            case NAVER: {
                redirectURL = naverOauth.getOauthRedirectURL();
            } break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
