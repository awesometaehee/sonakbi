package com.sonakbi.modules.like;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Likes {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Editor editor;
}

