package com.sonakbi.modules.like;

import com.sonakbi.modules.account.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Likes {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Account account;
}
