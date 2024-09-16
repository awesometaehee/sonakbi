package com.sonakbi.modules.editorTag.dto;

import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editorTag.EditorTag;
import com.sonakbi.modules.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EditorTagDto {

    private Long id;

    private Editor editor;

    private Tag tag;

    public EditorTagDto(EditorTag editorTag) {
        this.id = editorTag.getId();
        this.editor = editorTag.getEditor();
        this.tag = editorTag.getTag();
    }

    public static Set<EditorTagDto> from(Set<EditorTag> editorTag) {
        return editorTag.stream()
                .map(EditorTagDto::new)
                .collect(Collectors.toSet());
    }
}
