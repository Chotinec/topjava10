package ru.javawebinar.topjava.web.converter;


import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;

public class StringToLocaleDateTimeConverter implements Converter<String, LocalDateTime> {

    public StringToLocaleDateTimeConverter(){

    }

    @Override
    public LocalDateTime convert(String s) {
        return DateTimeUtil.parseLocalDateTime(s);
    }
}