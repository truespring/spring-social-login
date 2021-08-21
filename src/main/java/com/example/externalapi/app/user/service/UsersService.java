package com.example.externalapi.app.user.service;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.app.user.domain.entity.Users;

public interface UsersService {

    /**
     * social Login 을 통해 얻은 정보를 가공
     *
     * @param userEmail       사용자 sns 메일
     * @param socialLoginType sns 타입
     * @return 예정
     */
    Users getUserInfo(String userEmail, SocialLoginType socialLoginType);

    /**
     * DB 에 사용자 메일이 조회되지 않을 경우 DB 에 정보를 저장
     *
     * @param userEmail       사용자 sns 메일
     * @param socialLoginType sns 타입
     * @return 사용자 정보
     */
    Users signUpUser(String userEmail, SocialLoginType socialLoginType);
}
