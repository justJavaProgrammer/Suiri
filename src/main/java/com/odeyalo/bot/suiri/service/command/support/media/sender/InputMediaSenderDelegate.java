package com.odeyalo.bot.suiri.service.command.support.media.sender;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface InputMediaSenderDelegate {

    default void sendMedia(String chatId, String picture, String pictureType) {
        sendMedia(chatId, picture, pictureType, null, null);
    }

    default void sendMedia(String chatId, String picture, String pictureType, String caption) {
        sendMedia(chatId, picture, pictureType, null, caption);
    }

    /**
     * Delegate media sending to other senders
     */

    void sendMedia(String chatId, String picture, String pictureType, InlineKeyboardMarkup markupInline, String caption);

}
