package com.sonakbi.modules.follow.event;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.notification.Notification;
import com.sonakbi.modules.notification.NotificationRepository;
import com.sonakbi.modules.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class FollowEventListener {

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handlerFollowCreatedEvent(FollowCreateEvent followCreateEvent) {
        Account account = accountRepository.findById(followCreateEvent.getFollow().getFollowing().getId()).orElseThrow();
        Account followUser = accountRepository.findById(followCreateEvent.getFollow().getFollower().getId()).orElseThrow();

        if(account.isNotiFollowByWeb()) {
            Notification notification = Notification.builder()
                    .title("팔로우 소식이 있습니다.")
                    .link("/blog/" + account.getId() + "/post")
                    .message(followUser.getUserId() + "님이 팔로우했습니다.")
                    .checked(false)
                    .createdDateTime(LocalDateTime.now())
                    .account(account)
                    .notificationType(NotificationType.NOTIFICATION_FOLLOW)
                    .build();

            notificationRepository.save(notification);
        }
    }
}
