package com.example.externalapi.app.common.service;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.app.common.dto.CallbackDto;
import com.example.externalapi.app.user.domain.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CallBackServiceImpl implements CallBackService{

    public CallbackDto callBackInfo(String accessTokenStr, SocialLoginType socialLoginType, Users userInfo) {
        return CallbackDto.builder()
                .accessTokenStr(accessTokenStr)
                .socialLoginType(socialLoginType)
                .userInfo(userInfo)
                .build();
    }
}
