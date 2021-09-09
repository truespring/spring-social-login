package com.example.externalapi.app.common.service;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.app.common.dto.CallbackDto;
import com.example.externalapi.app.user.domain.entity.Users;

public interface CallBackService {

    CallbackDto callBackInfo(String accessTokenStr, SocialLoginType socialLoginType, Users userInfo);
}
