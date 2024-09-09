package com.sonakbi.modules.notification;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.util.Chrono;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Notification {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String link;

    private String message;

    private boolean checked;

    @ManyToOne
    private Account account;

    private LocalDateTime createdDateTime;

    public String getCreatedDateTime() {
        return Chrono.timesAgo(this.createdDateTime);
    }

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
