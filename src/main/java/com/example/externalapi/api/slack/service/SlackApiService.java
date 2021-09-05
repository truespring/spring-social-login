package com.example.externalapi.api.slack.service;

import com.example.externalapi.api.constants.SocialLoginType;
import org.json.simple.JSONObject;

public interface SlackApiService {

    default void sendSlack(SocialLoginType socialLoginType, String email) {
    }

    default void sendSlackBody(String text) {
    }
}
