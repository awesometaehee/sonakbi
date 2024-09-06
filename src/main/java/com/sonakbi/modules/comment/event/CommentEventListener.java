package com.sonakbi.modules.comment.event;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorRepository;
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
public class CommentEventListener {

    private final EditorRepository editorRepository;
    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @EventListener
    public void handlerCommentCreatedEvent(CommentCreatedEvent commentCreatedEvent) {
        Account account = accountRepository.findById(commentCreatedEvent.getEditor().getWriter().getId()).orElseThrow();
        if(account.isNotiCommentByWeb()) {
            Notification notification = Notification.builder()
                    .title("댓글이 등록됐습니다.")
                    .link("/blog/" + account.getId() + "/view/" + commentCreatedEvent.getEditor().getUrl())
                    .message(commentCreatedEvent.getComment().getContent())
                    .checked(false)
                    .createdDateTime(LocalDateTime.now())
                    .account(account)
                    .notificationType(NotificationType.NOTIFICATION_COMMENT)
                    .build();

            notificationRepository.save(notification);
        }
    }

}
