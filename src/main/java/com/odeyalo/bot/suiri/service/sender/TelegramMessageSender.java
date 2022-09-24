package com.odeyalo.bot.suiri.service.sender;

public interface TelegramMessageSender {
    /**
     * Send the message to user by chat id
     * @param chatId - chat id
     * @param text - message text
     */
    void send(String chatId, String text);

}
