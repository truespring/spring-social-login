package com.example.externalapi.api.domain.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleOauthResponse {

    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String idToken;
}
