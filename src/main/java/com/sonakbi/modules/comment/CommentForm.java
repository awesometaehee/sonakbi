package com.sonakbi.modules.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentForm {

    private String content;

    public CommentForm(String content) {
        this.content = content;
    }
}
