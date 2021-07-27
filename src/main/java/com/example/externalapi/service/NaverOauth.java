package com.example.externalapi.service;

import org.springframework.stereotype.Component;

@Component
public class NaverOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}