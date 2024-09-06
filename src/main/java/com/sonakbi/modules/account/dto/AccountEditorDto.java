package com.sonakbi.modules.account.dto;

import com.sonakbi.modules.account.Account;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountEditorDto {

    private Long id;

    private String userId;

    private String profileImage;

    public static AccountEditorDto from(Account account) {
        return AccountEditorDto.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .profileImage(account.getProfileImage())
                .build();
    }
}
