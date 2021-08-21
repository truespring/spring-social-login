package com.example.externalapi.common.user.service;

import com.example.externalapi.api.constants.SocialLoginType;
import com.example.externalapi.common.user.domain.entity.Users;
import com.example.externalapi.common.user.domain.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public String getUserInfo(String userEmail, SocialLoginType socialLoginType) {
        Optional<Users> userInfo = usersRepository.findAllByUserEmail(userEmail);
        if (userInfo.isEmpty()) {
            signUpUser(userEmail, socialLoginType);
            return "회원가입";
        }
        // 로그인 승인
        return "로그인";
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
