package com.sonakbi.modules.account;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private String shortDescription;

    private LocalDateTime joinedAt;

    private String authority;

    public String getProfileImage() {
        return profileImage == null ? "/images/account-icon.png" : profileImage;
    }
}
