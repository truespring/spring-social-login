package com.example.externalapi.api.domain.naver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverOauthResponse {

    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String idToken;
}
