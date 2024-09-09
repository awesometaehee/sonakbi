package com.sonakbi.modules.notification;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String getNotification(@CurrentAccount Account account, Model model) {
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, false);
        long numberOfChecked = notificationRepository.countByAccountAndChecked(account, true);
        putCategorizedNotifications(account, model, notifications, numberOfChecked, notifications.size());

        notificationService.markAsRead(notifications);

        model.addAttribute("isNew", true);

        return "notification/list";
    }

    @GetMapping("/notifications/old")
    public String getOldNotifications(@CurrentAccount Account account, Model model) {
        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDateTimeDesc(account, true);
        long numberOfChecked = notificationRepository.countByAccountAndChecked(account, false);
        putCategorizedNotifications(account, model, notifications, notifications.size(), numberOfChecked);

        model.addAttribute("isNew", false);

        return "notification/list";
    }

    private void putCategorizedNotifications(Account account, Model model, List<Notification> notifications, long numberOfChecked, long numberOfNotChecked) {
        List<Notification> commentNotifications = new ArrayList<>();
        List<Notification> likeNotifications = new ArrayList<>();
        List<Notification> followNotifications = new ArrayList<>();

        for(Notification n : notifications) {
            switch (n.getNotificationType()) {
                case NOTIFICATION_COMMENT : commentNotifications.add(n); break;
                case NOTIFICATION_LIKE : likeNotifications.add(n); break;
                case NOTIFICATION_FOLLOW : followNotifications.add(n); break;
            }
        }

        model.addAttribute(account);
        model.addAttribute("notifications", notifications);
        model.addAttribute("commentNotifications", commentNotifications);
        model.addAttribute("likeNotifications", likeNotifications);
        model.addAttribute("followNotifications", followNotifications);
        model.addAttribute("numberOfChecked", numberOfChecked);
        model.addAttribute("numberOfNotChecked", numberOfNotChecked);
    }
}
