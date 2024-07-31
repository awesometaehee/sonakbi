package com.sonakbi.modules.account;


import jakarta.persistence.*;
import lombok.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

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

    public String getProfileImage() {
        return profileImage == null ? "/images/account-icon.png" : profileImage;
    }

    public String getAccountPath(String path) {
        return URLEncoder.encode(path, UTF_8);
    }
}
