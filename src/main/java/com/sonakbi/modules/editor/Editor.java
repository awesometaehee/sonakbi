package com.sonakbi.modules.editor;

import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Editor {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String mainText;

    @ManyToMany
    private Set<Tag> tags;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String thumbnail;

    private boolean disclosure;

    @ManyToMany
    private Set<Series> series;

    private LocalDateTime publishedTime;
}
