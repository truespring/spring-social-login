package com.example.externalapi.service;

import org.springframework.stereotype.Component;

@Component
public class KakaoOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }
}