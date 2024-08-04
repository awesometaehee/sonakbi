package com.sonakbi.modules.tag;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TagCountDto {

    private String value;
    private long count;

    public TagCountDto(String value, long count) {
        this.value = value;
        this.count = count;
    }
}
