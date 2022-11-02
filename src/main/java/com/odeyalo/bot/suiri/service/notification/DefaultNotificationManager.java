package com.odeyalo.bot.suiri.service.notification;

import com.odeyalo.bot.suiri.entity.User;
import com.odeyalo.bot.suiri.repository.UserRepository;
import com.odeyalo.bot.suiri.service.sender.AdvancedTelegramMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * Default implementation of NotificationManager
 * @see NotificationManager
 */
@Service
public class DefaultNotificationManager implements NotificationManager {
    private final UserRepository userRepository;
    private final AdvancedTelegramMessageSender messageSender;
    private static final boolean ENABLED_NOTIFICATIONS_FLAG = true;

    @Autowired
    public DefaultNotificationManager(UserRepository userRepository, AdvancedTelegramMessageSender messageSender) {
        this.userRepository = userRepository;
        this.messageSender = messageSender;
    }

    @Override
    public List<User> getUsersWithEnabledNotifications() {
        return userRepository.findAllByUserSettingsEnableNotification(ENABLED_NOTIFICATIONS_FLAG);
    }

    @Override
    public void sendNotifications(List<SendMessage> messages) {
        for (SendMessage message : messages) {
            sendNotification(message);
        }
    }

    @Override
    public void sendNotification(SendMessage sendMessage) {
        this.messageSender.send(sendMessage);
    }
}
