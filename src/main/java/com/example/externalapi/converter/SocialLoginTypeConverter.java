package com.example.externalapi.converter;

import com.example.externalapi.constants.SocialLoginType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * Controller 에서 socialLoginType 파라미터(@PathVariable 을 통해)를 받는데
 * enum 타입의 대문자 값을 소문자로 매핑 가능하도록 하기위해 생성
 * 만약 converter 가 구현되어 있지 않다면 http://localhost:8080/auth/GOOGLE
 * 의 형태로 파라미터가 전달되어야 함
 * 따라서 SocialLoginTypeConverter 를 통해 http://localhost:8080/auth/google
 * 형태의 파라미터가 전달되었을때 생성해준 SocialLoginType 의 GOOGLE 값에 매팽이 될 수 있음
 */
@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {

    @Override
    public SocialLoginType convert(String s) {
        return SocialLoginType.valueOf(s.toUpperCase());
    }
}