package com.odeyalo.bot.suiri.service.sender;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Advanced message sender for telegram API that add additional methods
 */
public interface AdvancedTelegramMessageSender extends TelegramMessageSender {

    /**
     * Default overrode method from TelegramMessageSender to simplify child classes override methods
     * @param chatId - chat id
     * @param text - message text
     */
    @Override
    default void send(String chatId, String text) {
        send(new SendMessage(chatId, text));
    }



    void send(SendMessage message);

}
