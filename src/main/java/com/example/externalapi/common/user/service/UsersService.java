package com.example.externalapi.common.user.service;

import com.example.externalapi.api.constants.SocialLoginType;

public interface UsersService {

    /**
     * social Login 을 통해 얻은 정보를 가공
     * @return
     */
    String getUserInfo(String userEmail, SocialLoginType socialLoginType);
}
