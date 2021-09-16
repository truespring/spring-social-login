package com.example.externalapi;

import com.example.externalapi.app.common.dto.TestEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

@RunWith(SpringRunner.class)
@ActiveProfiles("")
//@SpringBootTest
public class JacksonTests {

    // 출처 : https://alwayspr.tistory.com/31
    Logger log = (Logger) LoggerFactory.getLogger(TestEntity.class);

    private final ObjectMapper objectMapper;

    public JacksonTests() {
        this.objectMapper = new ObjectMapper();
    }

    // 모든 컬럼 출력
    @Test
    public void always() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        log.info("{}", result);
    }

    // null 이 아닌 컬럼 출력
    @Test
    public void non_null() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        log.info(":: {}", result);
    }

    //
    @Test
    public void non_absent() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        log.info(":: {}", result);
    }

    //
    @Test
    public void non_empty() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        log.info(":: {}", result);
    }

    //
    @Test
    public void non_default() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        log.info(":: {}", result);
    }
}
