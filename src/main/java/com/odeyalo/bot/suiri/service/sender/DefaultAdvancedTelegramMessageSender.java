package com.odeyalo.bot.suiri.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * AdvancedTelegramMessageSender implementation that use AbsSender to execute message sending
 * @version 1.0.0
 * @see com.odeyalo.bot.suiri.service.sender.AdvancedTelegramMessageSender
 * @see com.odeyalo.bot.suiri.service.sender.TelegramMessageSender
 */
@Service
public class DefaultAdvancedTelegramMessageSender implements AdvancedTelegramMessageSender {
    private final AbsSender absSender;
    private final Logger logger = LoggerFactory.getLogger(DefaultAdvancedTelegramMessageSender.class);

    @Autowired
    public DefaultAdvancedTelegramMessageSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    /**
     * Send telegram message and log it
     * @param message - message to send
     */
    @Override
    public void send(SendMessage message) {
        try {
            this.absSender.execute(message);
        } catch (TelegramApiException exception) {
            this.logger.error("Failed to send message: {}", message, exception);
        }
    }
}
