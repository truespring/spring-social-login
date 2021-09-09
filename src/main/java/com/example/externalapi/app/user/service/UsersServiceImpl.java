package com.example.externalapi.app.user.service;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.api.slack.service.SlackApiServiceImpl;
import com.example.externalapi.app.user.domain.entity.Users;
import com.example.externalapi.app.user.domain.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final SlackApiServiceImpl slackApiService;

    @Override
    public Users getUserInfo(String userEmail, SocialLoginType socialLoginType) {
        Optional<Users> userInfo = usersRepository.findByUserEmail(userEmail);
        if (userInfo.isEmpty()) {
            // 회원가입
            slackApiService.sendSlack(socialLoginType, userEmail);
            return signUpUser(userEmail, socialLoginType);
        }
        // 로그인 승인
        return userInfo.get();
    }

    @Override
    public Users signUpUser(String userEmail, SocialLoginType socialLoginType) {
        return usersRepository.save(Users.builder()
                .userEmail(userEmail)
                .userPw(userEmail + socialLoginType) // TODO 두 가지를 조합하여 암호화 예정
                .socialLoginType(socialLoginType)
                .build());
    }
}
