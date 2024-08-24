package com.sonakbi.modules.follow;

import com.sonakbi.modules.account.Account;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Account follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Account following;

    public boolean isFollowing(Account currentUser, Account targetUser) {
        return this.follower.equals(currentUser) && this.following.equals(targetUser);
    }

}
