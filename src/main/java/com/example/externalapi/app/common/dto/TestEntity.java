package com.example.externalapi.app.common.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Getter
public class TestEntity {

    private final String string;
    private final String emptyString;
    private final Object nullValue;
    private final int number;
    private final int zero;
    private final List<String> list;
    private final List<String> emptyList;
    private final Date date;
    private final Date zeroDate;
    private final Optional<String> optional;
    private final Optional<String> emptyOptional;

    public TestEntity() {
        this.string = "민수"; // 문자열
        this.emptyString = ""; // 빈문자열
        this.nullValue = null; // null
        this.number = 100; // 정수
        this.zero = 0; // 0인 정수
        this.list = Arrays.asList("민수", "원우"); // 리스트
        this.emptyList = emptyList(); // 빈리스트
        date = new Date(); // 날짜
        zeroDate = new Date(0L); // 빈날짜
        this.optional = Optional.of("민수"); // 값이 있는 Optional
        this.emptyOptional = Optional.empty(); // 빈 Optional
    }
}
