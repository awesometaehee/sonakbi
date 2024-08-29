package com.sonakbi.modules.comment;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.util.Chrono;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    public String getCreatedAt() {
        return Chrono.timesAgo(this.createdAt);
    }

    @ManyToOne(fetch = FetchType.LAZY) // 연관 Entity를 꼭 필요할 때만 로드하도록 LAZY를 권장
    private Editor editor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    @OrderBy("id asc")
    private List<Comment> childrenComment = new ArrayList<>();
}
