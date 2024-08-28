package com.sonakbi.modules.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sonakbi.modules.account.Account;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CommentDto {

    private String content;

    private Long commentId;

    private String userId;

    private String profileImage;

    private String createdAt;

    private List<Comment> childrenComment;
}
