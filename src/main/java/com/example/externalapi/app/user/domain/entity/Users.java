package com.example.externalapi.app.user.domain.entity;

import com.example.externalapi.api.constants.SocialLoginType;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Getter
@ToString
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long userSeq;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_pw")
    private String userPw;

    // google, kakao, naver, github
    @Column(name = "user_social_type")
    @Enumerated(EnumType.STRING)
    private SocialLoginType socialLoginType;
}
