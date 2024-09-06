package com.sonakbi.modules.editor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.dto.AccountEditorDto;
import com.sonakbi.modules.comment.CommentDto;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.util.Chrono;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter
@NoArgsConstructor
public class EditorDto {

    private Long id;

    private String title;

    private String mainText;

    private String description;

    private AccountEditorDto writer;

    private String url;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String thumbnail;

    private boolean disclosure;

    private String publishedTime;

    private int likeCount;

    private int commentCount;

    private int orderId;

    @JsonProperty("comments")
    private List<CommentDto> comments = new ArrayList<>();

    // 필요한 생성자 추가
    public EditorDto(Long id, String title, String mainText, String description, Account writer,
                     String url, String thumbnail, boolean disclosure, LocalDateTime publishedTime,
                     int likeCount, int commentCount, int orderId) {
        this.id = id;
        this.title = title;
        this.mainText = mainText;
        this.description = description;
        this.writer = AccountEditorDto.from(writer);
        this.url = url;
        this.thumbnail = thumbnail;
        this.disclosure = disclosure;
        this.publishedTime = Chrono.timesAgo(publishedTime);
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.orderId = orderId;
        this.comments = CommentDto.from(writer.getComments());
        // this.comments = writer != null ? writer.getComments() : new ArrayList<>();
    }

    public EditorDto(Editor editor) {
        this.id = editor.getId();
        this.title = editor.getTitle();
        this.mainText = editor.getMainText();
        this.description = editor.getDescription();
        this.writer = AccountEditorDto.from(editor.getWriter());
        this.url = editor.getUrl();
        this.thumbnail = editor.getThumbnail();
        this.disclosure = editor.isDisclosure();
        this.publishedTime = editor.getPublishedTime();
        this.likeCount = editor.getLikeCount();
        this.commentCount = editor.getCommentCount();
        this.orderId = editor.getOrderId();
        this.comments = CommentDto.from(editor.getWriter().getComments());
        // this.comments = editor.getWriter().getComments();
    }

    public static List<EditorDto> from(List<Editor> editors) {
        return editors.stream()
                .map(EditorDto::new)
                .collect(Collectors.toList());
    }

    public boolean useThumbnail() {
        return !(this.thumbnail == null);
    }
}
