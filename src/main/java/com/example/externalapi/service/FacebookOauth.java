package com.example.externalapi.service;

import org.springframework.stereotype.Component;

@Component
public class FacebookOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}