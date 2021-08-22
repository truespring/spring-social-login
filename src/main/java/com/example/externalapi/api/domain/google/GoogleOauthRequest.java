package com.example.externalapi.api.domain.google;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoogleOauthRequest {

    private String responseType;
    private String clientId;
    private String redirectUri;
    private String state;
    private String scope;
    private String clientSecret;
    private String code;
    private String accessType;
    private String grantType;
    private String includeGrantedScopes;
    private String loginHint;
    private String prompt;
}
