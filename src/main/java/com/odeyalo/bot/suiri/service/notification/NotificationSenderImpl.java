package com.odeyalo.bot.suiri.service.notification;

import com.odeyalo.bot.suiri.service.sender.AdvancedTelegramMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;


/**
 * Send message list of messages to users
 *
 * @version 1.0.0
 * @see NotificationSender
 */
@Service
public class NotificationSenderImpl implements NotificationSender {
    private final AdvancedTelegramMessageSender telegramMessageSender;

    @Autowired
    public NotificationSenderImpl(AdvancedTelegramMessageSender telegramMessageSender) {
        this.telegramMessageSender = telegramMessageSender;
    }

    @Override
    public void sendNotification(List<SendMessage> messages) {
        for (SendMessage message : messages) {
            this.telegramMessageSender.send(message);
        }
    }
}
