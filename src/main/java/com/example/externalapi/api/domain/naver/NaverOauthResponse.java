package com.example.externalapi.api.domain.naver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverOauthResponse {

    private String resultcode;
    private String message;
    private NaverResponse response;
}
