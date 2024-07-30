package com.sonakbi.modules.tag;

import com.sonakbi.modules.editorTag.EditorTag;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class Tag {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String value;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EditorTag> editorTags = new HashSet<>();
}
