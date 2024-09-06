package com.sonakbi.modules.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sonakbi.modules.account.Account;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CommentDto {

    private String content;

    private Long commentId;

    private String userId;

    private String profileImage;

    private String createdAt;

    private List<Comment> childrenComment;

    public CommentDto(Comment comment) {
        this.content = comment.getContent();
        this.commentId = comment.getId();
        this.userId = comment.getAccount().getUserId();
        this.profileImage = comment.getAccount().getProfileImage();
        this.createdAt = comment.getCreatedAt();
        this.childrenComment = comment.getChildrenComment();
    }

    public static List<CommentDto> from(List<Comment> comments) {
        return comments.stream()
                .map(CommentDto::new)
                .collect(Collectors.toList());
    }
}
