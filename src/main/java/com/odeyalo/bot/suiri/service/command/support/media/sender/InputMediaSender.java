package com.odeyalo.bot.suiri.service.command.support.media.sender;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * Send specific InputMedia
 */
public interface InputMediaSender {

    void send(String chatId, InputMedia media);


    void send(String chatId, InputMedia media, InlineKeyboardMarkup markupInline, String caption);


    <T extends PartialBotApiMethod<Message>> void send(T message);


    String senderType();
}
