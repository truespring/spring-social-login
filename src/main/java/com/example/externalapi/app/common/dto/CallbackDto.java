package com.example.externalapi.app.common.dto;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.app.user.domain.entity.Users;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CallbackDto {

    private String accessTokenStr;
    private SocialLoginType socialLoginType;
    private Users userInfo;
}
