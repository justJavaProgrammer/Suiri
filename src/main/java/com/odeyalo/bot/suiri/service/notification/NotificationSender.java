package com.odeyalo.bot.suiri.service.notification;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * Send notifications to users
 */
public interface NotificationSender {
    /**
     * Send notification all users in list
     * @param messages - list of messages to send
     */
    void sendNotification(List<SendMessage> messages);
}
