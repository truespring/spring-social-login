package com.example.externalapi;

import com.example.externalapi.app.common.dto.TestEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("")
//@SpringBootTest
public class JacksonTests {

    // 출처 : https://alwayspr.tistory.com/31

    private final ObjectMapper objectMapper;

    public JacksonTests() {
        this.objectMapper = new ObjectMapper();
    }

    // 모든 컬럼 출력
    @Test
    public void always() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        System.out.println(result);
    }

    // null 이 아닌 컬럼 출력
    @Test
    public void non_null() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        System.out.println(result);
    }

    //
    @Test
    public void non_absent() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        System.out.println(result);
    }

    //
    @Test
    public void non_empty() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        System.out.println(result);
    }

    //
    @Test
    public void non_default() throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        String result = this.objectMapper.writeValueAsString(new TestEntity());

        System.out.println(result);
    }
}
