package com.sonakbi.modules.editor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.UserAccount;
import com.sonakbi.modules.comment.Comment;
import com.sonakbi.modules.editorTag.EditorTag;
import com.sonakbi.modules.like.Likes;
import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
@NamedEntityGraph(name = "Editor.withTags", attributeNodes = {
        @NamedAttributeNode("editorTags"),
        @NamedAttributeNode("writer"),
        @NamedAttributeNode("editorTags"),
        @NamedAttributeNode("likes"),
})
public class Editor {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String title;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private String mainText;

    @Size(max = 150)
    private String description;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account writer;

    @Column(unique = true, nullable = false)
    private String url;

    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("editor")
    private Set<EditorTag> editorTags = new HashSet<>();

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String thumbnail;

    private boolean disclosure = true;

    @ManyToMany
    private List<Series> series;

    private LocalDateTime publishedTime;

    @OneToMany
    private List<Likes> likes = new ArrayList<>();

    /**
     * List : 순서 보장
     * Set : 중복 허용 X
     * 댓글 기능 경우 순서가 유지되는 게 중요하므로 List
     */
    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private int commentCount;

    public void setWrite(Account account) {
        this.writer = account;
        this.publishedTime = LocalDateTime.now();
    }

    public boolean isWriter(UserAccount userAccount) {
        return this.writer.equals(userAccount.getAccount());
    }

    public boolean isWriterAndDisclosure(UserAccount userAccount, boolean disclosure) {
        if(this.isWriter(userAccount)) {
            return true;
        }

        return !this.isWriter(userAccount) && disclosure;
    }

    public boolean useThumbnail() {
        return !this.getThumbnail().isEmpty();
    }

    public void addTags(Tag tag) {
        for(EditorTag editorTag : editorTags) {
            if(editorTag.getTag().equals(tag)) {
                return;
            }
        }

        EditorTag editorTag = new EditorTag(this, tag);
        this.editorTags.add(editorTag);
        tag.getEditorTags().add(editorTag);
    }

    public void removeAllTags() {
        for(Iterator<EditorTag> iterator = editorTags.iterator(); iterator.hasNext();) {
            EditorTag editorTag = iterator.next();
            iterator.remove();
            editorTag.getTag().getEditorTags().remove(editorTag);
            editorTag.setEditor(null);
            editorTag.setTag(null);
        }
    }

    public void addComment() {
        this.commentCount++;
    }

    public void removeComment() {
        this.commentCount--;
    }

    public boolean checkIfExistingWriter(Editor editor, Account account) {
        return editor.getWriter().equals(account);
    }
}