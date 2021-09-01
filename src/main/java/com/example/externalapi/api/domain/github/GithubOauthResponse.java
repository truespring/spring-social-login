package com.example.externalapi.api.domain.github;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GithubOauthResponse {
    private String email;
    private Boolean primary;
    private Boolean verified;
    private String visibility;
}
