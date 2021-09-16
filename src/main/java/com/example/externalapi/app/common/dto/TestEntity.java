package com.example.externalapi.app.common.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Getter
public class TestEntity {

    private String string;
    private String emptyString;
    private Object nullValue;
    private int number;
    private int zero;
    private List<String> list;
    private List<String> emptyList;
    private Date date;
    private Date zeroDate;
    private Optional<String> optional;
    private Optional<String> emptyOptional;

    public TestEntity() {
        this.string = "민수";
        this.emptyString = "";
        this.nullValue = null;
        this.number = 100;
        this.zero = 0;
        this.list = Arrays.asList("민수", "원우");
        this.emptyList = emptyList();
        date = new Date();
        zeroDate = new Date(0L);
        this.optional = Optional.of("민수");
        this.emptyOptional = Optional.empty();
    }
}
