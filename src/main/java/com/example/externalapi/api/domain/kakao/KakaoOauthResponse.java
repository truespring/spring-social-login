package com.example.externalapi.api.domain.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoOauthResponse {

    private String connected_at;
    private Long id;
    private KakaoAccount kakao_account;
}

