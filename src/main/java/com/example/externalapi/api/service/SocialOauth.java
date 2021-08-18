package com.example.externalapi.api.service;

import com.example.externalapi.api.constants.SocialLoginType;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

/**
 * service 역할
 */
public interface SocialOauth {

    /**
     * 각 Social Login 페이지로 Redirect 처리할 URL Build
     * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
     */
    String getOauthRedirectURL();

    /**
     * API Server 로부터 받은 code 를 활용하여 사용자 인증 정보 요청
     *
     * @param code API Server 에서 받아온 code
     * @return API 서버로 부터 응답받은 Json 형태의 결과를 string 으로 반환
     */
    String requestAccessToken(String code);

    /**
     * accessToken 을 통해 로그인한 사용자의 정보를 요청
     */
    String requestUserInfo(String accessToken);

    /**
     * interface 에서 메소드를 구현하는 방법 - default
     * 해당되는 소셜 타입을 반환
     *
     * @return enum
     */
    default SocialLoginType type() {
        if (this instanceof GithubOauth) {
            return SocialLoginType.GITHUB;
        } else if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        } else if (this instanceof NaverOauth) {
            return SocialLoginType.NAVER;
        } else if (this instanceof KakaoOauth) {
            return SocialLoginType.KAKAO;
        } else {
            return null;
        }
    }
}
