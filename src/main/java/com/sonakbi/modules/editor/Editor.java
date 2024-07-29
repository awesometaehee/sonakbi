package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.UserAccount;
import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor @NoArgsConstructor
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

    @ManyToMany
    private List<Tag> tags;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String thumbnail;

    private boolean disclosure = true;

    @ManyToMany
    private List<Series> series;

    private LocalDateTime publishedTime;

    private Long like = 0L;

    public void setWrite(Account account) {
        this.writer = account;
        this.publishedTime = LocalDateTime.now();
    }

    public boolean isWriter(UserAccount userAccount) {
        return this.writer.equals(userAccount.getAccount());
    }
}