package com.example.externalapi.api.service;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.app.user.domain.entity.Users;
import com.example.externalapi.app.user.service.UsersServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * serviceImpl 역할
 */
@Service
@AllArgsConstructor
public class OauthService {

    private final List<SocialOauth> socialOauthList; // implements 하는 클래스를 리스트로 만드는 것
    private final UsersServiceImpl usersServiceImpl;

    /**
     * 어떤 URL 로 리다이렉트 할 것인지 판단
     *
     * @param socialLoginType 소셜 로그인 타입
     * @return socialLoginPage
     */
    public String request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.getOauthRedirectURL();
    }

    /**
     * 발급 받은 accessToken 을 다시 api 로 보내어 사용자 정보를 받음
     *
     * @param socialLoginType 소셜 로그인 타입
     * @param accessToken     소셜 토큰
     * @return 로그인 / 회원가입
     */
    public Users requestUserInfo(SocialLoginType socialLoginType, String accessToken) throws Exception {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String userEmail = socialOauth.requestUserInfo(accessToken);
        return usersServiceImpl.getUserInfo(userEmail, socialLoginType);
    }

    /**
     * accessToken 발급을 위한 요청
     *
     * @param socialLoginType 소셜 로그인 타입
     * @param code            api 의 코드
     * @return accessToken 요청
     */
    public String requestAccessToken(SocialLoginType socialLoginType, String code) throws JsonProcessingException {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.requestAccessToken(code);
    }

    /**
     * [GOOGLE, GITHUB, NAVER, KAKAO] 중 해당되는 소셜 로그인 타입인지 확인
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
