package com.odeyalo.bot.suiri.service.sender;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramPhotoSender {

    void sendPhoto(SendPhoto photo) throws TelegramApiException;
}
