package com.example.externalapi.api.domain.naver;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NaverOauthRequest {

    private String redirectUri;
    private String clientId;
    private String state;
    private String scope;
    private String responseType;
    private String code;
    private String grantType;
    private String clientSecret;
//    private String error;
//    private String errorDescription;
}
