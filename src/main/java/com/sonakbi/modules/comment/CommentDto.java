package com.sonakbi.modules.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sonakbi.modules.account.Account;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CommentDto {

    private String content;

    private String userId;

    private String profileImage;

    private String createdAt;
}
