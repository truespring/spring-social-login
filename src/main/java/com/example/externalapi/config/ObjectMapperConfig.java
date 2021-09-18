package com.example.externalapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    private static final ObjectMapper mapper = new ObjectMapper();

    private ObjectMapperConfig() {}

    public static ObjectMapper getInstance() {
        return mapper;
    }
}
