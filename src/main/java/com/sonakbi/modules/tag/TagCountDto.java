package com.sonakbi.modules.tag;

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
