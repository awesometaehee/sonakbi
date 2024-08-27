package com.sonakbi.modules.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AccountFollowDto {

    private Long id;

    private String userId;

    private String profileImage;

    private String shortDescription;

    private boolean isFollowing;

    public boolean isFollowAccount(Long accountId) {
        return this.id.equals(accountId);
    }
}
