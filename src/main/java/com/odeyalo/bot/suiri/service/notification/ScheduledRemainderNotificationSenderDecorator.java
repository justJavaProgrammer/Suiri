package com.odeyalo.bot.suiri.service.notification;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.support.lang.LanguageResponseMessageResolverRegistry;
import com.odeyalo.bot.suiri.support.lang.LanguageResponseMessageResolverStrategy;
import com.odeyalo.bot.suiri.support.lang.Languages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled RemainderNotificationSenderDecorator that send messages on same time
 * This decorator resolves users with enabled notification, gets their preferred language, build messages and send it
 */
@Service
public class ScheduledRemainderNotificationSenderDecorator implements RemainderNotificationSenderDecorator {
    private final NotificationManager notificationManager;
    private final LanguageResponseMessageResolverRegistry container;
    private final Logger logger = LoggerFactory.getLogger(ScheduledRemainderNotificationSenderDecorator.class);

    @Autowired
    public ScheduledRemainderNotificationSenderDecorator(NotificationManager notificationManager, LanguageResponseMessageResolverRegistry container) {
        this.notificationManager = notificationManager;
        this.container = container;
    }

    @Override
    @Scheduled(fixedDelayString = "${remainder.notification.time.seconds}", timeUnit = TimeUnit.SECONDS)
    public void send() {
        this.logger.info("Starting sending remainder notifications to users");
        List<User> users = this.notificationManager.getUsersWithEnabledNotifications();
        for (User user : users) {
            LanguageResponseMessageResolverStrategy resolver = this.container.getOrDefault(user.getUserSettings().getLanguage(), Languages.ENGLISH);
            String message = resolver.getMessage(NotificationLanguagePropertiesConstants.REMAINDER_NOTIFICATION_MESSAGE_PROPERTY_KEY);
            this.notificationManager.sendNotification(new NotificationMessage(user.getTelegramId(), message));
        }
    }
}
