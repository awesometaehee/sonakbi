package com.sonakbi.modules.like.event;

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
public class LikesEventListener {

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handlerLikesCreatedEvent(LikesCreateEvent likesCreateEvent) {
        Account account = accountRepository.findById(likesCreateEvent.getEditor().getWriter().getId()).orElseThrow();
        Account likedUser = accountRepository.findById(likesCreateEvent.getLikes().getAccount().getId()).orElseThrow();

        if(account.isNotiLikeByWeb()) {
            Notification notification = Notification.builder()
                    .title("좋아요 알림이 있습니다.")
                    .link("/blog/" + account.getId() + "/view/" + likesCreateEvent.getEditor().getUrl())
                    .message(likedUser.getUserId() + "님이 좋아요를 눌렀습니다.")
                    .checked(false)
                    .createdDateTime(LocalDateTime.now())
                    .account(account)
                    .notificationType(NotificationType.NOTIFICATION_LIKE)
                    .build();

            notificationRepository.save(notification);
        }
    }
}
