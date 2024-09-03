package com.sonakbi.modules.editorTag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.tag.Tag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor @AllArgsConstructor
public class EditorTag {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Editor editor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    public EditorTag(Editor editor, Tag tag) {
        this.editor = editor;
        this.tag = tag;
    }
}
