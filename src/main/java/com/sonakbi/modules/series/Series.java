package com.sonakbi.modules.series;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Series {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String title;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String thumbnailImage;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Editor> editor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public void removeEditor(Editor editor) {
        this.getEditor().remove(editor);
    }
}
