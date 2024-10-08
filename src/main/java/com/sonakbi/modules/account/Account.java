package com.sonakbi.modules.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sonakbi.modules.comment.Comment;
import com.sonakbi.modules.follow.Follow;
import com.sonakbi.modules.like.Likes;
import com.sonakbi.modules.series.Series;
import jakarta.persistence.*;
import lombok.*;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String userId;

    private String email;

    private String password;

    private String url;

    private String location;

    private String company;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private String shortDescription;

    private LocalDateTime joinedAt;

    private String authority;

    private String aboutDescription;

    private boolean notiCommentByWeb = true;

    private boolean notiLikeByWeb = true;

    private boolean notiFollowByWeb = true;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Follow> following;

    @OneToMany(mappedBy = "following", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Follow> followers;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Series> series = new ArrayList<>();

    public String getProfileImage() {
        return profileImage == null ? "/images/account-icon.png" : profileImage;
    }

    public String getAccountPath(String path) {
        return URLEncoder.encode(path, UTF_8);
    }

    public boolean checkEqualAccount(Account userAccount, Account postAccount) {
        return userAccount.equals(postAccount);
    }

    public boolean isWriter(UserAccount account) {
        return this.userId.equals(account.getUsername());
    }

}
