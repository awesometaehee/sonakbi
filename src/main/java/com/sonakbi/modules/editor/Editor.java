package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String title;

    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private String mainText;

    @OneToOne
    private Account writer;

    @Column(nullable = false)
    private String url;

    @ManyToMany
    private Set<Tag> tags;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String thumbnail;

    private boolean disclosure;

    @ManyToMany
    private Set<Series> series;

    private LocalDateTime publishedTime;
}
