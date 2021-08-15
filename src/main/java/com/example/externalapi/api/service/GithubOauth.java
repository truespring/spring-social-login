package com.example.externalapi.api.service;

import org.springframework.stereotype.Component;

@Component
public class GithubOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }
}