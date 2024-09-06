package com.sonakbi.modules.comment.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentForm {

    private String content;

    private Long parentId;

    public CommentForm(String content, Long parentId) {
        this.content = content;
        this.parentId = parentId;
    }
}
