package com.odeyalo.bot.suiri.service.notification;

import com.odeyalo.bot.suiri.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manager that working with notifications. Provides functionality to send notification to user, resolve users by notification settings
 */
public interface NotificationManager {

    /**
     * Resolve users with enabled notifications
     * @return - list of users with enabled notifications
     */
    List<User> getUsersWithEnabledNotifications();

    /**
     * Send message by NotificationMessage
     * @param messages - list of messages to send
     */
    default void sendNotificationMessages(List<NotificationMessage> messages) {
        // Transform messages to List of SendMessage to delegate actual sending to sendNotifications(List<SendMessage> messages) method
        List<SendMessage> sendMessages = messages.stream().map(message -> new SendMessage(message.getChatId(), message.getMessage())).collect(Collectors.toList());
        sendNotifications(sendMessages);
    }

    /**
     * Send message by SendMessage class that support inline keyboard, etc
     * @param messages - list of messages to send
     */
    void sendNotifications(List<SendMessage> messages);

    /**
     * Send notification to user
     * @param notificationMessage - message to send
     */
    default void sendNotification(NotificationMessage notificationMessage) {
        String message = notificationMessage.getMessage();
        String chatId = notificationMessage.getChatId();
        sendNotification(new SendMessage(chatId, message));
    }

    /**
     * Send notification to user using SendMessage with inline keyboard support, etc
     * @param sendMessage - message to send
     */
    void sendNotification(SendMessage sendMessage);
}
